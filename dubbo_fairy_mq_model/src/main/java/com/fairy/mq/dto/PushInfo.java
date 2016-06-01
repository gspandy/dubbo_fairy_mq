package com.fairy.mq.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: [andongxu]
 * @CreateDate: [2016/3/2]
 * @Version: [v1.0]
 * 生产消息实体类
 */
public class PushInfo implements Serializable {
    /*
    * 请求唯一标示
    * */
    @NotBlank(message = "请求唯一标示")
    private String reqUniId;
    /*
    * 业务系统标识符
    * */
    @NotBlank(message = "请业务系统标识")
    private String systemid;
    /*
    * 是否需要加密,0：否,1：是
    * */
    @Pattern(regexp = "[0,1]")
    private String isSec;
    /*
    * 是否需要事务,0:否，1:是(目前暂不支持事务)
    * */
    @Pattern(regexp = "[0,1]")
    private String isTransactional;
    /*
    * 消息服务topic
    * */
    @NotBlank(message = "消息服务topic")
    private String topic;
    /*
    * 消息服务group
    * */
    private String goupName;
    /*
    * 消息服务tag
    * */
    private String tagName;
    /*
    * 是否需要有序消费,0:否,1:是
    * */
    @Pattern(regexp = "[0,1]")
    private String isOrderly;
    /*
    * 消息体
    * */
    @NotNull(message = "消息体不允许为空")
    private List<PushMsg> msg;

    public String getReqUniId() {
        return reqUniId;
    }

    public void setReqUniId(String reqUniId) {
        this.reqUniId = reqUniId;
    }

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getIsSec() {
        return isSec;
    }

    public void setIsSec(String isSec) {
        this.isSec = isSec;
    }

    public String getIsTransactional() {
        return isTransactional;
    }

    public void setIsTransactional(String isTransactional) {
        this.isTransactional = isTransactional;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGoupName() {
        return goupName;
    }

    public void setGoupName(String goupName) {
        this.goupName = goupName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getIsOrderly() {
        return isOrderly;
    }

    public void setIsOrderly(String isOrderly) {
        this.isOrderly = isOrderly;
    }

    public List<PushMsg> getMsg() {
        return msg;
    }

    public void setMsg(List<PushMsg> msg) {
        this.msg = msg;
    }
}
