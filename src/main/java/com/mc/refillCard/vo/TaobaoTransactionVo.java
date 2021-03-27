package com.mc.refillCard.vo;

import lombok.Data;

import java.io.Serializable;

/****
 * @Author: MC
 * @Description:Transaction构建
 * @Date 2021-3-20 21:17:23
 *****/
@Data
public class TaobaoTransactionVo implements Serializable{

	/**
	 * 更新发货状态
	 */
	private Boolean DoDummySend;

	/**
	 * 想发送给该买家的旺旺消息
	 */
	private String AliwwMsg;

	/**
	 * 更新备注
	 */
	private TaobaoDoMemoUpdateVo DoMemoUpdate;

}
