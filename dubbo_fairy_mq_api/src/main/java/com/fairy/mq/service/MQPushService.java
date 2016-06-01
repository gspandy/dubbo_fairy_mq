package com.fairy.mq.service;

/**
 * MQ 消息服务
 * @Author andongxu
 * @Create time 16-2-29:下午5:25
 * @Version
 * @Last update time
 */
public interface MQPushService {

    /**
     * 为业务系统统一提供的生产消息的接口
     * @param pushInfo json化后的PushInfo字符串
     * @return json化后的PushResult字符串
     */
    String pushInfo(String pushInfo);
}
