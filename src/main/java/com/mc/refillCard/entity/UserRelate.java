package com.mc.refillCard.entity;

import java.io.Serializable;

/****
 * @Author: MC
 * @Description:UserRelate构建
 * @Date 2021-3-26 16:48:32
 *****/
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



	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public String getAccessToken() {
		return accessToken;
	}

	//set方法
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	//get方法
	public String getAgisoUserId() {
		return agisoUserId;
	}

	//set方法
	public void setAgisoUserId(String agisoUserId) {
		this.agisoUserId = agisoUserId;
	}
	//get方法
	public String getFuliAppKey() {
		return fuliAppKey;
	}

	//set方法
	public void setFuliAppKey(String fuliAppKey) {
		this.fuliAppKey = fuliAppKey;
	}
	//get方法
	public String getFuluSercret() {
		return fuluSercret;
	}

	//set方法
	public void setFuluSercret(String fuluSercret) {
		this.fuluSercret = fuluSercret;
	}
	//get方法
	public Long getUserId() {
		return userId;
	}

	//set方法
	public void setUserId(Long userId) {
		this.userId = userId;
	}


}
