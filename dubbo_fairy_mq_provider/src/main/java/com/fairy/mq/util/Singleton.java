/**
* 巨商汇平台 版权所有 Copyright@2014
*/
package com.fairy.mq.util;

import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* [懒汉式单例类.在第一次调用的时候实例化自己]
*
* @ProjectName: [gooday-services]
* @Author: [liuxiaolong]
* @CreateDate: [2015/4/9 11:54]
* @Update: [说明本次修改内容] BY[liuxiaolong][2015/4/9]
* @Version: [v1.0]
*/
public class Singleton {

    private Logger logger = LoggerFactory.getLogger(Singleton.class);

    //私有的默认构造子
    private Singleton() {}
    //注意，这里没有final
    private static DefaultMQProducer producer = null;
    //静态工厂方法
    public static DefaultMQProducer getDefaultMQProducerInstance() throws MQClientException {
        if (producer == null) {
            producer = new DefaultMQProducer(ConfigUtil.getInstance().get("producerGroupName"));
            producer.setNamesrvAddr(ConfigUtil.getInstance().get("namesrvAddr"));
            producer.setDefaultTopicQueueNums(Integer.valueOf(ConfigUtil.getInstance().get("defaultTopicQueueNums")));
            producer.setCreateTopicKey(ConfigUtil.getInstance().get("defaultTopic"));
            producer.setCompressMsgBodyOverHowmuch(Integer.valueOf(ConfigUtil.getInstance().get("compressMsgBodyOverHowmuch")));
            producer.setMaxMessageSize(Integer.valueOf(ConfigUtil.getInstance().get("maxMessageSize")));
            producer.setRetryAnotherBrokerWhenNotStoreOK(Boolean.valueOf(ConfigUtil.getInstance().get("retryAnotherBrokerWhenNotStoreOK")));
            producer.setRetryTimesWhenSendFailed(Integer.valueOf(ConfigUtil.getInstance().get("retryTimesWhenSendFailed")));
            producer.setSendMsgTimeout(Integer.valueOf(ConfigUtil.getInstance().get("sendMsgTimeout")));
            producer.setUnitMode(Boolean.valueOf(ConfigUtil.getInstance().get("unitMode")));
            /**
             * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
             * 注意：切记不可以在每次发送消息时，都调用start方法
             */
            producer.start();
        }
        return producer;
    }

    public static void shutDown() {
        try {
            if (producer != null) {
                producer.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
