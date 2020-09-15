package com.coderman.backend.common;


import java.util.HashMap;
import java.util.Map;


public class JsonData {

    public static final int SUCCESS_CODE=0;  //响应成功
    public static final int ERROR_CODE=-1;   //响应失败
    public static final int PARAM_ERROR=400; //参数错误
    public static final int NOT_LOGIN=100;   //用户未登入
    public static final int INTERNAL_SERVER_ERROR=500; //服务器异常
    public static final int UNAUTHORIZED = 401; //登入后访问资源未授权

    private int code;

    private String msg;

    private Object data;

    public JsonData(int code) {
        this.code = code;
    }

    public static JsonData success(Object object, String msg) {
        JsonData jsonData = new JsonData(SUCCESS_CODE);
        jsonData.data = object;
        jsonData.msg = msg;
        return jsonData;
    }

    public static JsonData success(Object object) {
        JsonData jsonData = new JsonData(SUCCESS_CODE);
        jsonData.data = object;
        return jsonData;
    }

    public static JsonData success() {
        return new JsonData(SUCCESS_CODE);
    }

    public static JsonData fail(String msg) {
        JsonData jsonData = new JsonData(ERROR_CODE);
        jsonData.msg = msg;
        return jsonData;
    }

    public static JsonData fail(int code,String msg) {
        JsonData jsonData = new JsonData(code);
        jsonData.msg = msg;
        return jsonData;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("code", code);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }

    public static int getSuccessCode() {
        return SUCCESS_CODE;
    }

    public static int getErrorCode() {
        return ERROR_CODE;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
