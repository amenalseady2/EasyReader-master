package com.BlackDiamond2010.hzs.bean.ProductDetails;

public class MessageUtil {

    @Override
    public String toString() {
        return "MessageUtil{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    /**
     * code : 400
     * msg : 原手机号码相同不能修改
     */

    private int code;
    private String msg;

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
}
