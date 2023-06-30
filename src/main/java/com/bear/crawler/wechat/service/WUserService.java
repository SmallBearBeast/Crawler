package com.bear.crawler.wechat.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.bear.crawler.basic.http.OkHttp;
import com.bear.crawler.wechat.dao.WUserInfoDao;
import com.bear.crawler.wechat.mybatis.generator.po.WUserInfoPO;
import com.bear.crawler.wechat.pojo.dto.WUserInfoDto;
import com.bear.crawler.wechat.pojo.dto.resp.UserInfosRespDto;
import com.bear.crawler.wechat.provider.WUserInfoProvider;
import com.bear.crawler.wechat.util.BeanConverterUtil;
import com.bear.crawler.wechat.util.OtherUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class WUserService {

    @Autowired
    private WUserInfoProvider wUserInfoProvider;

    @Autowired
    private WUserInfoDao wUserInfoDao;

    @Autowired
    private OkHttp okHttp;

    public void syncUserInfos() {
        Set<String> openIdSet = new HashSet<>();
        syncUserInfosInternal(0, openIdSet);
        removeUnFollowUserInfo(openIdSet);
    }

    private void syncUserInfosInternal(int offset, Set<String> openIdSet) {
        log.debug("syncUserInfosInternal: offset = {}", offset);
        String url = "https://mp.weixin.qq.com/cgi-bin/user_tag?action=get_user_list&groupid=-2&begin_openid=-1&begin_create_time=-1&limit=20&offset=0&backfoward=1&token={{token}}&lang=zh_CN&f=json&ajax=1&random=0.8786165673080111";
        String newUrl = OtherUtil.getNewUrlByParams(url, MapUtil.of("offset", offset));
        UserInfosRespDto respDto = okHttp.get(newUrl, null, null, UserInfosRespDto.class);
        List<WUserInfoDto> userInfoDtos = respDto.getUserInfos();
        if (CollectionUtil.isEmpty(userInfoDtos)) {
            log.debug("syncUserInfosInternal: sync end");
            return;
        }
        for (WUserInfoDto dto : userInfoDtos) {
            WUserInfoPO userInfoPO = BeanConverterUtil.dtoToPo(dto);
            WUserInfoPO saveUserInfoPO = wUserInfoProvider.findByOpenId(userInfoPO.getOpenid());
            if (saveUserInfoPO != null) {
                userInfoPO.setId(saveUserInfoPO.getId());
                wUserInfoDao.updateByOpenId(userInfoPO);
            } else {
                wUserInfoDao.insert(userInfoPO);
            }
            wUserInfoProvider.updateCache(userInfoPO, false);
            openIdSet.add(userInfoPO.getOpenid());
        }
        syncUserInfosInternal(offset + userInfoDtos.size(), openIdSet);
    }

    private void removeUnFollowUserInfo(Set<String> openIdSet) {
        List<WUserInfoPO> userInfos = wUserInfoProvider.getAllUserInfos();
        for (WUserInfoPO userInfo : userInfos) {
            if (!openIdSet.contains(userInfo.getOpenid())) {
                wUserInfoDao.deleteByOpenId(userInfo);
                wUserInfoProvider.updateCache(userInfo, true);
            }
        }
    }
}
