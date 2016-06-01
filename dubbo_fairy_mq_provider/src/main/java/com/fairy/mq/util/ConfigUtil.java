package com.fairy.mq.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2015-09-23.
 */
public class ConfigUtil {

    public static void main(String[] args) throws IOException {
//        double s = ConfigUtil.getInstance().getDouble("framework.jdbc.maxActive");
//        System.out.println(Double.valueOf(s));
    }

    private static ConfigUtil instance;

    public static ConfigUtil getInstance()  {
        if (instance == null) {
            try {
                instance = new ConfigUtil();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }
        return instance;
    }

    private Properties properties;

    public Properties getProperties() {
        return properties;
    }

    private ConfigUtil() throws IOException {
        try {
            InputStream confInput = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties");
            init(confInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(InputStream inputStream) throws IOException {
        this.properties = new Properties();
        this.properties.load(inputStream);
    }

    public String get(String key) {
        try{
            if (this.properties.getProperty(key) == null)
                return "";
            return this.properties.getProperty(key);
        }catch(Exception e){
            return "";
        }
    }

    public Integer getInteger(String key){
        try{
            if (null==this.properties.getProperty(key) || "".equals(this.properties.getProperty(key)))
                return 0;
            return Integer.valueOf(this.properties.getProperty(key));
        }catch(Exception e){
            return 0;
        }
    }

    public Double getDouble(String key){
        try{
            if (null==this.properties.getProperty(key) || "".equals(this.properties.getProperty(key)))
                return 0d;
            return Double.valueOf(this.properties.getProperty(key));
        }catch(Exception e){
            return 0d;
        }
    }

}
