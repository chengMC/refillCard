package com.mc.refillCard.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/****
 * @Author: MC
 * @Description:UserRelate构建
 * @Date 2021-3-26 16:48:32
 *****/
@Data
public class UserRelate implements Serializable{

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
	 * 福禄key
	 */
	private String fuliAppKey;

	/**
	 * 福禄密钥
	 */
	private String fuluSercret;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 1 淘宝
	 */
	private Integer platform;

	/**
	 * 余额
	 */
	private BigDecimal balance;

}
