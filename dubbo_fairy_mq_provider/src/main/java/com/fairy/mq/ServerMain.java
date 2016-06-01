package com.fairy.mq;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Hello world!
 */
public class ServerMain {
    private static ClassPathXmlApplicationContext context;


    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger(ServerMain.class);
        logger.info("--------------开始启动--------");
        System.out.println("start........");
        com.alibaba.dubbo.container.Main.main(args);
        System.out.println("start........end");
        logger.info("--------------启动 完毕--------");
        /*com.alibaba.dubbo.container.Main.main(args);*/
    }
}
