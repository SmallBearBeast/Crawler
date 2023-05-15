package com.bear.crawler.webmagic.processor;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlQuery;
import com.bear.crawler.webmagic.mybatis.generator.mapper.WechatOfficialAccountPOMapper;
import com.bear.crawler.webmagic.mybatis.generator.po.WechatOfficialAccountPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WechatOfficialAccountPOExample;
import com.bear.crawler.webmagic.pojo.dto.CommonRespDto;
import com.bear.crawler.webmagic.pojo.dto.WechatOfficialAccountDto;
import com.bear.crawler.webmagic.pojo.dto.WechatOfficialAccountListRespDto;
import com.bear.crawler.webmagic.util.OtherUtil;
import com.bear.crawler.webmagic.util.TransformBeanUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class WechatAccountProcessor implements PageProcessor, InitializingBean {

    private static final String BEGIN = "begin";
    private static final int ACCOUNT_LIMIT = 50;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WechatOfficialAccountPOMapper wechatOfficialAccountPOMapper;

    private List<WechatOfficialAccountPO> wechatOfficialAccountPOList = new ArrayList<>();

    @Override
    public void process(Page page) {
        try {
            WechatOfficialAccountListRespDto accountListRespDto = objectMapper.readValue(page.getRawText(), WechatOfficialAccountListRespDto.class);
            CommonRespDto commonRespDto = accountListRespDto.getCommonRespDto();
            if (commonRespDto != null) {
                int ret = commonRespDto.getRet();
                String errMsg = commonRespDto.getErrMsg();
                if (ret == 0) {
                    int begin = getBegin(page);
                    List<WechatOfficialAccountDto> accountDtoList = accountListRespDto.getOfficialAccountDtoList();
                    if (accountDtoList == null) {
                        log.info("accountDtoList is null");
                    } else {
                        if (accountDtoList.isEmpty()) {
                            log.info("Load official account list to end, begin = {}", begin);
                        } else {
                            log.info("Load official account list successfully, begin = {}", begin);
                            for (WechatOfficialAccountDto accountDto : accountDtoList) {
                                if (!isInAccountDB(accountDto)) {
                                    wechatOfficialAccountPOMapper.insert(TransformBeanUtil.dtoToPo(accountDto));
                                }
                            }
                            OtherUtil.sleep(3);
                            if (begin + accountDtoList.size() > ACCOUNT_LIMIT) {
                                log.info("Load official account list more than {}", ACCOUNT_LIMIT);
                            } else {
                                addNextTargetRequest(page, begin);
                            }
                        }
                    }
                } else if (ret == 200013) {
                    log.warn("The account has been blocked and needs to wait for a few hours to be unblocked, ret = {}, err_msg = {}", ret, errMsg);
                } else if (ret == 200002) {
                    log.warn("Parameter error, check the fakeid, ret = {}, err_msg = {}", ret, errMsg);
                } else {
                    log.warn("Load official account list error, ret = {}, err_msg = {}", ret, errMsg);
                }
            }
        } catch (Exception e) {
            log.error("process fail, e = {}", e.getMessage());
        }
    }

    @Override
    public Site getSite() {
        return Site.me()
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36 Edg/112.0.1722.48")
                .setCharset("utf-8")
                .setTimeOut(10 * 1000)
                .setRetryTimes(3)
                .setRetrySleepTime(3000);
    }

    @Override
    public void afterPropertiesSet() {
        try {
            WechatOfficialAccountPOExample example = new WechatOfficialAccountPOExample();
            List<WechatOfficialAccountPO> officialAccountPOList = wechatOfficialAccountPOMapper.selectByExample(example);
            wechatOfficialAccountPOList.addAll(officialAccountPOList);
        } catch (Exception e) {
            log.warn("Init the official account list failed");
        }
    }

    private int getBegin(Page page) {
        String url = page.getUrl().get();
        UrlQuery urlQuery = UrlBuilder.of(url).getQuery();
        return Integer.parseInt(String.valueOf(urlQuery.get(BEGIN)));
    }

    private void addNextTargetRequest(Page page, int begin) {
        String url = page.getUrl().get();
        UrlQuery urlQuery = UrlBuilder.of(url).getQuery();
        UrlQuery newUrlQuery = new UrlQuery();
        for (Map.Entry<CharSequence, CharSequence> entry : urlQuery.getQueryMap().entrySet()) {
            if (!BEGIN.contentEquals(entry.getKey())) {
                newUrlQuery.add(entry.getKey(), entry.getValue());
            }
        }
        newUrlQuery.add(BEGIN, begin + 5);
        String nextUrl = UrlBuilder.of(url).setQuery(newUrlQuery).build();
        page.addTargetRequest(nextUrl);
    }

    private boolean isInAccountDB(WechatOfficialAccountDto accountDto) {
        for (WechatOfficialAccountPO accountPO : wechatOfficialAccountPOList) {
            if (accountPO.getFakeId().equals(accountDto.getFakeId())) {
                return true;
            }
        }
        return false;
    }

    // TODO: 5/15/23 private boolean checkChange()
}
