/**
 * 巨商汇平台 版权所有 Copyright@2014
 */
package com.fairy.mq.service;

import com.alibaba.fastjson.JSON;
import com.fairy.mq.dto.OperLog;
import com.fairy.mq.dto.PushInfo;
import com.fairy.mq.dto.PushMsg;
import com.fairy.mq.dto.PushResult;
import com.fairy.mq.exception.ValidateException;
import com.fairy.mq.util.Constans;
import com.fairy.mq.util.MQHelper;
import com.fairy.mq.util.SystemRegister;
import com.fairy.mq.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * MQ消息服务实现
 *
 * @Author: andongxu
 * @CreateDate: [2016/2/1 10:40]
 * @Update:
 * @Version: [v1.0]
 */
@Component("MQPushService")
public class MQPushServiceImpl implements MQPushService {

    private Logger logger = LoggerFactory.getLogger(MQPushServiceImpl.class);

    @Bean
    public SystemRegister systemRegister() {
        return new SystemRegister();
    }

    @Autowired
    private SystemRegister systemRegister;

    /*
    * 为业务系统统一提供的生产消息的接口
    * @Parameter String
    * json化后的PushInfo字符串
    * @Return String
    * json化后的PushResult字符串
    * */
    @Override
    public String pushInfo(String jsonInfo) {
        logger.info("request data: " + jsonInfo);
        PushResult rtn = null;
        //报文转为json
        PushInfo pushInfo = null;
        try {
            pushInfo = JSON.parseObject(jsonInfo, PushInfo.class);
        } catch (Exception e) {
            rtn = new PushResult(Constans.ERROR_0004);
            return JSON.toJSONString(rtn);
        }
        //验证是否支持该系统
        if (!systemRegister.exists(pushInfo.getSystemid())) {
            logger.error(Constans.ERROR_0007[1]);
            rtn = new PushResult(Constans.ERROR_0007);
            return JSON.toJSONString(rtn);
        }
        //报文格式验证
        try {
            ValidatorUtil.validate(pushInfo);
        } catch (ValidateException e) {
            logger.error("数据格式验证异常", e);
            PushResult result = new PushResult(Constans.ERROR_0005, e.getMessage(), pushInfo.getReqUniId());
            return JSON.toJSONString(result);
        }
        //检查分布式事务
        if (Constans.IS_TRANSACTION.equals(pushInfo.getIsTransactional())) {
            rtn = new PushResult(Constans.ERROR_0006[0], Constans.ERROR_0006[1], pushInfo.getReqUniId());
            return JSON.toJSONString(rtn);
        }
        //发送消息
        if (Constans.IS_ORDER_SEND.equals(pushInfo.getIsOrderly())) {
            return this.pushInfoOrderly(pushInfo);
        } else {
            return this.pushInfoDefault(pushInfo);
        }
    }

    /*
    * 无序方式发送消息
    * */
    private String pushInfoDefault(PushInfo pushInfo) {
        PushResult rtn = null;
        MQHelper mqHelper = new MQHelper();

        if (null != pushInfo.getMsg() && pushInfo.getMsg().size() > 0) {
            for (PushMsg o : pushInfo.getMsg()) {
                try {
                    rtn = mqHelper.pushData(pushInfo.getTopic(), pushInfo.getGoupName(), pushInfo.getTagName(), pushInfo.getReqUniId(), JSON.toJSONString(o));
                    logger.info(new OperLog(pushInfo.getReqUniId(), JSON.toJSONString(pushInfo), JSON.toJSONString(rtn), rtn.getCode(), pushInfo.getSystemid()).toString());
                    /*
                    if (rtn.getCode().equals(Constans.SUCC_0000[0])) {
                        logger.info(new OperLog(pushInfo.getReqUniId(), JSON.toJSONString(pushInfo), JSON.toJSONString(rtn), Constans.SEND_OK, pushInfo.getSystemid()).toString());
                    } else {
                        logger.error(new OperLog(pushInfo.getReqUniId(), JSON.toJSONString(pushInfo), JSON.toJSONString(rtn), Constans.SEND_FAILD, pushInfo.getSystemid()).toString());
                    }
                    */
                } catch (Exception e) {
                    logger.error("发布MQ消息异常", e);
                }
            }
        }
        return JSON.toJSONString(rtn);
    }

    /* * 有序方式发送消息 * */
    private String pushInfoOrderly(PushInfo pushInfo) {
        PushResult rtn = null;
        MQHelper mqHelper = new MQHelper();
        if (null != pushInfo.getMsg() && pushInfo.getMsg().size() > 0) {
            for (PushMsg o : pushInfo.getMsg()) {
                try {
                    rtn = mqHelper.pushDataOrderLy(pushInfo.getTopic(), pushInfo.getGoupName(), pushInfo.getTagName(), pushInfo.getReqUniId(), JSON.toJSONString(o), pushInfo.getMsg().size());
                    logger.info(new OperLog(pushInfo.getReqUniId(), JSON.toJSONString(pushInfo), JSON.toJSONString(rtn), rtn.getCode(), pushInfo.getSystemid()).toString());
                    /*
                    if (rtn.getCode().equals(Constans.SUCC_0000)) {
                        logger.info(new OperLog(pushInfo.getReqUniId(), JSON.toJSONString(pushInfo), JSON.toJSONString(rtn), Constans.SEND_OK, pushInfo.getSystemid()).toString());
                    } else {
                        logger.error(new OperLog(pushInfo.getReqUniId(), JSON.toJSONString(pushInfo), JSON.toJSONString(rtn), Constans.SEND_FAILD, pushInfo.getSystemid()).toString());
                    }
                    */
                } catch (Exception e) {
                    logger.error("发布MQ消息异常", e);
                }
            }
        }
        return JSON.toJSONString(rtn);
    }
}
