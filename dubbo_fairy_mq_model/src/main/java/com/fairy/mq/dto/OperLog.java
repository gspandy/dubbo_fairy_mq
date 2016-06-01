package com.fairy.mq.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @ProjectName: [gooday-services]
 * @Author: [lishijie]
 * @CreateDate: [2015/8/28]
 * @Version: [v1.0]
 * 日志实体类
 */
public class OperLog implements Serializable {
    /*
    * id
    * */
    private BigInteger id;
    /*
    * 请求唯一标识
    * */
    private String reqUniId;
    /*
    * 发送消息
    * */
    private String sendInfo;
    /*
    * 返回值
    * */
    private String returnInfo;
    /*
    * 状态
    * */
    private String state;
    /*
    * 创建时间
    * */
    private Date createTime;
    /*
    * 创建对象
    * */
    private String creator;

    /*
    * 带参数构造
    * */
    public OperLog(String reqUniId, String sendInfo, String returnInfo, String state,String creator) {
        this.reqUniId = reqUniId;
        this.sendInfo = sendInfo;
        this.returnInfo = returnInfo;
        this.state = state;
        this.creator=creator;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getReqUniId() {
        return reqUniId;
    }

    public void setReqUniId(String reqUniId) {
        this.reqUniId = reqUniId;
    }

    public String getSendInfo() {
        return sendInfo;
    }

    public void setSendInfo(String sendInfo) {
        this.sendInfo = sendInfo;
    }

    public String getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(String returnInfo) {
        this.returnInfo = returnInfo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "OperLog{" +
                "id=" + id +
                ", reqUniId='" + reqUniId + '\'' +
                ", sendInfo='" + sendInfo + '\'' +
                ", returnInfo='" + returnInfo + '\'' +
                ", state='" + state + '\'' +
                ", createTime=" + createTime +
                ", creator='" + creator + '\'' +
                '}';
    }
}
