package com.BlackDiamond2010.hzs.ui.activity.lives.bean;

/**
 * EventBus消息实体
 */
public class MessageEvent extends BaseBean {
	private static final long serialVersionUID = 1L;
	private int status;
	private String message;

	public MessageEvent(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
