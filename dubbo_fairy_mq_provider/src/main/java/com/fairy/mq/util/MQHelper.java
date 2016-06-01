/**
 * 巨商汇平台 版权所有 Copyright@2014
 */
package com.fairy.mq.util;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.fairy.mq.dto.PushResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * [mq初始化和清理资源工具类]
 * <p>
 * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
 * 注意：ProducerGroupName需要由应用来保证唯一<br>
 * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
 * 因为服务器会回查这个Group下的任意一个Producer
 * </p>
 *
 * @ProjectName: [gooday-services]
 * @Author: [liuxiaolong]
 * @CreateDate: [2015/4/20 17:58]
 * @Update: [说明本次修改内容] BY[liuxiaolong][2015/4/20]
 * @Version: [v1.0]
 */
public class MQHelper {

    private Logger logger = LoggerFactory.getLogger(MQHelper.class);

    private DefaultMQProducer producer = null;

    private DefaultMQPushConsumer consumer = null;


    public MQHelper() {
        this.initShutdownHook();
    }

    /**
     * 发布消息
     * @param topic
     * @param group
     * @param tag
     * @param uniId
     * @param content
     * @return
     */
    public PushResult pushData(String topic, String group, String tag, String uniId, String content) {
        logger.info("content：{}", content);
        try {
            producer = Singleton.getDefaultMQProducerInstance();
            Message msg = new Message(topic, tag, uniId, content.getBytes());

            SendResult sendResult = producer.send(msg);
            logger.info("MsgId:" + sendResult.getMsgId() + ",发送内容为：{}", sendResult);
            return this.getPushResult(sendResult);
        } catch (Exception e) {
            logger.error("发送消息失败，失败异常为：", e);
            return new PushResult(Constans.ERROR_0004);
        }
    }

    public PushResult pushDataOrderLy(String topic, String group, String tag, String uniId, String content, final int size) {
        logger.info("content：{}", content);
        try {
            producer = Singleton.getDefaultMQProducerInstance();
            Message msg = new Message(topic, tag, uniId, content.getBytes());
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = size;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, uniId);

            logger.info("MsgId:" + sendResult.getMsgId() + ",发送内容为：{}", sendResult);
            return this.getPushResult(sendResult);
        } catch (Exception e) {
            logger.error("发送消息失败，失败异常为：", e);
            return new PushResult(Constans.ERROR_0004);
        }
    }

    private PushResult getPushResult(SendResult sendResult) {
        //消息収送成功
        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            return new PushResult(Constans.SUCC_0000[0], Constans.SUCC_0000[1], sendResult.getMsgId());
        }
        //消息収送成功，但是服务器刷盘超时，消息已经迕入服务器队列，只有此时服务器宕机，消息才会丢失
        else if (SendStatus.FLUSH_DISK_TIMEOUT.equals(sendResult.getSendStatus())) {
            return new PushResult(Constans.SUCC_0001[0], Constans.SUCC_0001[1], sendResult.getMsgId());
        }
        //消息収送成功，但是服务器同步到 Slave 时超时，消息已经迕入服务器队列，只有此时服务器宕机，消息才会丢失
        else if (SendStatus.FLUSH_SLAVE_TIMEOUT.equals(sendResult.getSendStatus())) {
            return new PushResult(Constans.SUCC_0002[0], Constans.SUCC_0002[1], sendResult.getMsgId());
        }
        //消息収送成功，但是此时 slave 丌可用，消息已经迕入服务器队列，只有此时服务器宕机，消息才会丢失
        else if (SendStatus.SLAVE_NOT_AVAILABLE.equals(sendResult.getSendStatus())) {
            return new PushResult(Constans.SUCC_0003[0], Constans.SUCC_0003[1], sendResult.getMsgId());
            //未知错误
        } else {
            return new PushResult(Constans.SUCC_9999[0], Constans.SUCC_9999[1], sendResult.getMsgId());
        }
    }

    /**
     * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从MetaQ服务器上注销自己
     * 注意：我们建议应用在JBOSS、Tomcat等容器的退出钩子里调用shutdown方法
     */
    public void initShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
//                logger.info("清理资源");
                Singleton.shutDown();
            }
        }));
    }
}
