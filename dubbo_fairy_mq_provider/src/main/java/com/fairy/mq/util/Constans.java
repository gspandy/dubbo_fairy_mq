/**
 * 巨商汇平台 版权所有 Copyright@2014
 */
package com.fairy.mq.util;

/**
 * 常量
 *
 * @ProjectName: [gooday-services]
 * @Author: [lishijie]
 * @CreateDate: [2015/8/19]
 * @Version: [v1.0]
 */
public interface Constans {

    /**
     * 使用分布式事务
     */
    String IS_TRANSACTION = "1";
    /**
     * 有序发送
     */
    String IS_ORDER_SEND = "1";
    /**
     * 消息収送成功
     */
    String[] SUCC_0000 = {"0000", "消息収送成功"};
    /**
     * 消息収送成功，但是服务器刷盘超时，消息已经迕入服务器队列
     */
    String[] SUCC_0001 = {"0001", "消息収送成功，但是服务器刷盘超时，消息已经迕入服务器队列"};
    /**
     * 消息収送成功，但是服务器同步到 Slave 时超时，消息已经迕入服务器队列
     */
    String[] SUCC_0002 = {"0002", "消息収送成功，但是服务器同步到 Slave 时超时，消息已经迕入服务器队列"};
    /**
     * 消息収送成功，但是此时 slave 不可用，消息已经迕入服务器队列
     */
    String[] SUCC_0003 = {"0003", "消息収送成功，但是此时 slave 不可用，消息已经迕入服务器队列"};
    /**
     * 请求报文转换异常
     */
    String[] ERROR_0004 = {"0004", "请求报文转换异常"};
    /**
     * 请求报文部格式错误
     */
    String ERROR_0005 = "0005";
    /**
     * Failed,暂时不支持分布式事务!
     */
    String[] ERROR_0006 = {"0006", "Failed,暂时不支持分布式事务"};
    /**
     * Failed,暂不支持该系统
     */
    String[] ERROR_0007 = {"0007", "Failed,暂不支持该系统"};

    String[] SUCC_9999 = {"9999", "未知错误"};

}
