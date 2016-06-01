package com.fairy.mq.dto;

import java.io.Serializable;

/**
 * @ProjectName: [gooday-services]
 * @Author: [lishijie]
 * @CreateDate: [2015/8/28]
 * @Version: [v1.0]
 */
public class PushResult implements Serializable {

    //状态码
    private String code;

    //状态信息
    private String msg;

    //请求时上送的唯一ID
    private String reqUniId;

    public PushResult() {

    }

    public PushResult(String code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public PushResult(String code,String msg, String reqUniId){
        this.code=code;
        this.msg=msg;
        this.reqUniId = reqUniId;
    }

    public PushResult(String[] arg){
        this.code=arg[0];
        this.msg=arg[1];
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReqUniId() {
        return reqUniId;
    }

    public void setReqUniId(String reqUniId) {
        this.reqUniId = reqUniId;
    }
}
