package org.apache.rocketmq.order.common.dto;

/**
 * @author snowalker
 * 错误码封装
 */
public class CodeMsg {

    private String code;
    private String msg;

    /**通用的错误码*/
    public static CodeMsg SUCCESS = new CodeMsg("10000", "SUCCESS");
    public static CodeMsg SERVER_ERROR = new CodeMsg("20000", "SERVER_ERROR");
    public static CodeMsg BIZ_ERROR = new CodeMsg("40004", "BIZ_ERROR");
    public static CodeMsg PARAM_INVALID = new CodeMsg("40005", "入参校验失败");

    public CodeMsg( ) {
    }

    public CodeMsg(String code, String msg ) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public CodeMsg setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public CodeMsg setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public CodeMsg fillArgs(Object ... args) {
        String code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }

}