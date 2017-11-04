package com.yuwubao.util;

/**
 * Created by xiwang on 2/13/17.
 */
public class RestApiResponse<T> {
    /**
     * 状态信息
     */
    private String message;
    /**
     * 状态码
     */
    private int status;

    /**
     * 结果
     */
    private T result;

    private String returnCode;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void updateStatusMessage(int status, String msg) {
        this.setMessage(msg);
        this.setStatus(status);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    /**
     * 成功之后的返回
     *
     * @param status
     * @param result
     */
    public void successResponse(int status, T result) {
        this.status = status;
        this.result = result;
    }

    /**
     * 成功之后的返回
     *
     * @param status
     * @param result
     */
    public void successResponse(int status, T result,String mess) {
        this.status = status;
        this.result = result;
        this.message = mess;
    }

    /**
     * 失败之后的返回
     *
     * @param message
     * @param status
     */
    public void failedApiResponse(int status, String message) {
        this.message = message;
        this.status = status;
    }
}
