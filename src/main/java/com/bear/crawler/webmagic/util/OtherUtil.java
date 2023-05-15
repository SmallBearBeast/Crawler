package com.bear.crawler.webmagic.util;

import java.util.Random;

public class OtherUtil {

    private static final Random random = new Random();

    public static void sleep(int second) {
        int sleepTime = (second + random.nextInt(second)) * 1000;
        try {
            Thread.sleep(sleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
