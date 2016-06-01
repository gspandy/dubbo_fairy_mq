package com.fairy.mq.util;/**
 * @Author andongxu
 * @Create 16-3-2:上午11:42
 * @Version
 * @Last update time
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @auth andongxu
 * @time 16-3-2 上午11:42
 */
public class SystemRegister {


    private static List<Sys> syss;

    static {
        String systems = ConfigUtil.getInstance().get("systems");
        syss = JSON.parseArray(systems, Sys.class);
    }

    public Boolean exists(String sysId) {
        for (Sys sys : syss) {
            if (sys.getSysId().equals(sysId)) return true;
        }
        return false;
    }

    public Sys getSys(String sysId) {
        for (Sys sys : syss) {
            if (sys.getSysId().equals(sysId)) return sys;
        }
        return null;
    }
}
