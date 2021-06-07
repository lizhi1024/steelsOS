package com.lzos.steels.admin.common;

import com.alibaba.fastjson.JSONObject;
import com.lzos.steels.admin.exception.BaseErrorInfoInterface;

public class ResultBody {

    /**
     * 响应代码
     */
    private String code;

    /*
     * 成功标识
     */
    private Boolean success;

    /**
     * 响应消息
     */
    private String message;

    /*
     * 异常信息
     */
    private String execptionTrace;

    /**
     * 响应结果
     */
    private Object result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExecptionTrace() {
        return execptionTrace;
    }

    public void setExecptionTrace(String execptionTrace) {
        this.execptionTrace = execptionTrace;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static ResultBody success(String code, String message, Object data) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setSuccess(true);
        rb.setMessage(message);
        rb.setResult(data);
        return rb;
    }

    /**
     * 异常
     */
    public static ResultBody error(String code, String message, String exceptionMsg) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setSuccess(false);
        rb.setMessage(message);
        rb.setExecptionTrace(exceptionMsg);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultBody failed(String code, String message) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setSuccess(false);
        rb.setMessage(message);
        return rb;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
