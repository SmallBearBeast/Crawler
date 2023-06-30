package com.bear.crawler.wechat.pipeline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Pipeline负责抽取结果的处理，包括计算、持久化到文件、数据库等。WebMagic默认提供了“输出到控制台”和“保存到文件”两种结果处理方案。
 */
public class WebMagicTestPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {

    }
}
