package com.mc.refillCard.vo;

import java.io.Serializable;

/****
 * @Author: MC
 * @Description:Goods构建
 * @Date 2021-3-28 20:28:52
 *****/
public class PushOrderResultVo implements Serializable{

	/**
	 *  成功失败
	 */
	private Boolean succeed;

	/**
	 * 返回消息
	 */
	private String message;

	public Boolean getSucceed() {
		return succeed;
	}

	public void setSucceed(Boolean succeed) {
		this.succeed = succeed;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
