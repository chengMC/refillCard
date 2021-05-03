package com.mc.refillCard.dto;

import lombok.Data;

import java.io.Serializable;

/****
 * @Author: MC
 * @Description:UserRelate构建
 * @Date 2021-3-21 16:20:43
 *****/
@Data
public class UserRelateDto implements Serializable{

	/**
	 * 
	 */
	private Long id;

	/**
	 * 阿奇索密钥
	 */
	private String accessToken;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 1 淘宝
	 */
	private Integer platform;

	private String startTime;

	private String endTime;

}
