package com.BlackDiamond2010.hzs.ui.activity.lives.bean;

/**
 * 类描述：
 * 创建人：yq
 * 创建时间：2016/7/14 11:50
 * 修改人：yq
 * 修改时间：2016/7/14 11:50
 * 修改备注：
 */
public class HttpResult<T>  extends BaseBean{

    /**
     * 默认约定返回 格式 ： {"status":0,"msg":"提示消息","content":{}}
     * status : 0
     * msg : 提示消息
     * content : {} 或 {[{},{},{}]}
     */
    private int code;
    private T data;
    private String msg;

    public void setStatus(int status) {
        this.code = status;
    }

    public int getStatus() {
        return code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getShowMessage() {
        return msg;
    }

    public void setShowMessage(String showMessage) {
        this.msg = showMessage;
    }

    @Override
    public String toString() {
        return "{" +
                "code:" + code +
                ",data:" + data +
                ",msg:'" + msg + '\'' +
                '}';

        /*return  "code:" + code +
                ",msg:" + msg +
                ",data:'" + data + '\'' +
                '}';*/
    }
}
