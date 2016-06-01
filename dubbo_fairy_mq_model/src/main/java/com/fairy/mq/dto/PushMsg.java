package com.fairy.mq.dto;

import java.io.Serializable;

/**
 * @ProjectName: [gooday-services]
 * @Author: [lishijie]
 * @CreateDate: [2015/8/28]
 * @Version: [v1.0]
 * 消息本体实体类
 */
public class PushMsg implements Serializable {
    /*
    * 消费时调用的接口名
    * */
    private String interfaceName;
    /*
    * 消费时调用的方法名
    * */
    private String interfaceMethod;
    /*
    * 系统标识符
    * */
    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    private String systemid;

    public String getInterfaceMethod() {
        return interfaceMethod;
    }

    public void setInterfaceMethod(String interfaceMethod) {
        this.interfaceMethod = interfaceMethod;
    }

    private String reqUniId;

    private String msgbody;

    public String getReqUniId() {
        return reqUniId;
    }

    public void setReqUniId(String reqUniId) {
        this.reqUniId = reqUniId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMsgbody() {
        return msgbody;
    }

    public void setMsgbody(String msgbody) {
        this.msgbody = msgbody;
    }
}
