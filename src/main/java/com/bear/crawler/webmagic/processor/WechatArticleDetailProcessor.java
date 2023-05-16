package com.bear.crawler.webmagic.processor;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@Component
@Slf4j
public class WechatArticleDetailProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        try {
            Selectable articleSelectable = page.getHtml().css("div#js_article");
            String articleTitle = articleSelectable.css("#activity-name").xpath("h1/text()").get().trim();
            String articleFrom = articleSelectable.css("#js_name").xpath("a/text()").get().trim();
            String articlePublishTime = articleSelectable.css("#publish_time").xpath("em/text()").get().trim();
            String articleLocation = articleSelectable.css("#js_ip_wording_wrp").xpath("em/text()").get().trim();
            log.debug("articleTitle = {}", articleTitle);
            log.debug("articleFrom = {}", articleFrom);
            log.debug("articlePublishTime = {}", articlePublishTime);
            log.debug("articleLocation = {}", articleLocation);

            Element contentElement = page.getHtml().getDocument().select("div#js_content").first();
            if (contentElement != null) {
                webMagicOrderNumber = 1;
                addNumberForElement(contentElement);
            }
            Selectable contentSelectable = page.getHtml().css("div#js_content");
            fetchContent(contentSelectable);
        } catch (Exception e) {
            log.error("e = {}", e.getMessage());
        }
    }

    @Override
    public Site getSite() {
        return Site.me()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
                .setCharset("utf-8")
                .setTimeOut(10 * 1000)
                .setRetryTimes(3)
                .setRetrySleepTime(3000);
    }

    private int webMagicOrderNumber = 1;

    private void addNumberForElement(Element originElement) {
        Elements elements = originElement.children();
        int size = elements.size();
        if (size <= 0) {
            originElement.attributes().add("web_magic_order_number", String.valueOf(webMagicOrderNumber));
            webMagicOrderNumber ++;
            return;
        }
        originElement.attributes().add("web_magic_order_number", String.valueOf(webMagicOrderNumber));
        webMagicOrderNumber ++;
        for (Element element : elements) {
            addNumberForElement(element);
        }
    }

    private void fetchContent(Selectable contentSelectable) {
        List<Selectable> pAndImgSelectables = contentSelectable.xpath("//p|//img|//span").nodes();
        // 按照web_magic_order_number排序，保证文章内容顺序
        pAndImgSelectables.sort((o1, o2) -> {
            try {
                int o1Number = Integer.parseInt(o1.xpath("/p/@web_magic_order_number|/img/@web_magic_order_number|/span/@web_magic_order_number").get().trim());
                int o2Number = Integer.parseInt(o2.xpath("/p/@web_magic_order_number|/img/@web_magic_order_number|/span/@web_magic_order_number").get().trim());
                return o1Number - o2Number;
            } catch (Exception e) {
                return 0;
            }
        });
        for (Selectable selectable : pAndImgSelectables) {
            String pText = selectable.xpath("/p/text()").get();
            if (pText != null && !pText.trim().isEmpty()) {
                log.debug("fetchContent: pText = {}", pText);
                continue;
            }
            String spanText = selectable.xpath("/span/text()").get();
            if (spanText != null && !spanText.trim().isEmpty()) {
                log.debug("fetchContent: spanText = {}", spanText);
                continue;
            }
            String imgUrl = selectable.xpath("/img/@data-src").get();
            if (imgUrl != null && !imgUrl.trim().isEmpty()) {
                log.debug("fetchContent: imgUrl = {}", imgUrl);
            }
        }
    }
}
