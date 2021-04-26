package com.mc.refillCard.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/****
 * @Author: MC
 * @Description:UserRelate构建
 * @Date 2021-3-26 16:48:32
 *****/
@Data
public class UserRelateVo implements Serializable{

	/**
	 * 
	 */
	private Long id;

	/**
	 * token
	 */
	private String accessToken;

	/**
	 * 阿奇索推送中的用户id
	 */
	private String agisoUserId;

	/**
	 * 用户id
	 */
	private Long userId;

	private String userName;

	private String nickName;

	/**
	 * 1 淘宝
	 */
	private Integer platform;

	/**
	 * 余额
	 */
	private BigDecimal balance;

}
