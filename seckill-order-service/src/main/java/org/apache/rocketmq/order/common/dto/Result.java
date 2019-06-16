package org.apache.rocketmq.order.common.dto;

/**
 * 返回体包装
 * @author snowalker
 * @param <T>
 */
public class Result<T> {

    private String code;
    private String msg;
    private T data;

    /**
     * 失败时候的调用
     */
    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<T>(codeMsg);
    }

    public static <T> Result<T> success(CodeMsg codeMsg, T t) {
        return new Result<T>(codeMsg.getCode(), codeMsg.getMsg(), t);
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String code, String msg, T t) {
        this.code = code;
        this.msg = msg;
        this.data = t;
    }

    private Result(CodeMsg codeMsg) {
        if (codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
            this.data = null;
        }
    }

    public String getCode() {
        return code;
    }

    public Result<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }
}