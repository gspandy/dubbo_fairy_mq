[TOC]
# MQ消息服务
目前只支持rocketmq，目前只实现了简单的消息通道，直接将mq服务返回的消息直接返回给调用者，未处理多种情况(FLUSH_DISK_TIMEOUT,FLUSH_SLAVE_TIMEOUT,SLAVE_NOT_AVAILABLE)的逻辑处理。

# 测试
1. 启动com.fairy.mq.service.Consumer
2. 执行MQPushService

# 待完善
1. 消息日志保存到mongodb