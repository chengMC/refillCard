package com.mc.refillCard.dto;

import lombok.Data;

import java.io.Serializable;

/****
 * @Author: MC
 * @Description:OriginalOrder构建
 * @Date 2021-3-21 14:41:57
 *****/
@Data
public class FuluOrderCardsDto implements Serializable{

	/**
	 *卡号
	 */
	private String card_number;

	/**
	 *密码
	 */
	private String card_pwd;

	/**
	 *卡密有效期
	 */
	private String card_deadline;



}
