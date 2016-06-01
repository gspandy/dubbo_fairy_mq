package com.fairy.mq.service;

import com.alibaba.fastjson.JSON;
import com.fairy.mq.SpringConfigLoader;
import com.fairy.mq.dto.PushInfo;
import com.fairy.mq.dto.PushMsg;
import com.fairy.mq.dto.PushResult;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * MQ消息服务
 *
 * @Author andongxu
 * @Create time 16-2-25:下午4:10
 * @Version
 * @Last update time
 */
public class MQPushServiceTest extends SpringConfigLoader {

    @Autowired
    private MQPushService lssMQPushService;

    @Test
    public void pushInfo() {
        //准备报文
        String uid = UUID.randomUUID().toString();

        PushMsg msg = new PushMsg();
        msg.setInterfaceName("interFaceName");
        msg.setReqUniId(uid);
        msg.setMsgbody("msgbody" + String.valueOf(new Random(9999).nextInt()));
        msg.setSystemid("S01");

        PushInfo pushInfo = new PushInfo();
        pushInfo.setTopic("group-1");
        pushInfo.setGoupName("fairy-mq-service-group-test-zj123");
        pushInfo.setSystemid("S01");
        pushInfo.setReqUniId(uid);
        pushInfo.setTagName("tag-a");
        ArrayList<PushMsg> list = new ArrayList<PushMsg>();
        list.add(msg);
        pushInfo.setMsg(list);
        pushInfo.setIsOrderly("1");

        String json = JSON.toJSONString(pushInfo);

        //发送消息
        String res = lssMQPushService.pushInfo(json);

        Assert.assertNotNull(res);
        PushResult pushResult = JSON.parseObject(res, PushResult.class);
        System.out.println("=================================" + res);
        Assert.assertEquals("0000", pushResult.getCode());
    }

    @Test
    public void validate() {
        //准备报文
        String uid = UUID.randomUUID().toString();

        PushMsg msg = new PushMsg();
        msg.setInterfaceName("interFaceName");
        msg.setReqUniId(uid);
        msg.setMsgbody("msgbody" + String.valueOf(new Random(9999).nextInt()));
        msg.setSystemid("systemA");

        PushInfo pushInfo = new PushInfo();
        pushInfo.setTopic("jsh-mq-service-test-zj123");
        pushInfo.setGoupName("jsh-mq-service-group-test-zj123");
        pushInfo.setSystemid("S01");
        pushInfo.setReqUniId(uid);
        ArrayList<PushMsg> list = new ArrayList<PushMsg>();
        list.add(msg);
        pushInfo.setMsg(list);
        pushInfo.setIsOrderly("1");

        String json = JSON.toJSONString(pushInfo);

        //发送消息
        String res = lssMQPushService.pushInfo(json);

        Assert.assertNotNull(res);
        PushResult pushResult = JSON.parseObject(res, PushResult.class);
        System.out.println("=================================" + res);
        Assert.assertNotEquals("0000", pushResult);
    }

}
