package com.mc.refillCard.dto;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
/****
 * @Author: MC
 * @Description:UserRelate构建
 * @Date 2021-3-21 16:20:43
 *****/
public class UserRelateDto implements Serializable{

	/**
	 * 
	 */
	private Long id;

	/**
	 * 阿奇索密钥
	 */
	private String agisoAppSecret;

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
	public String getAgisoAppSecret() {
		return agisoAppSecret;
	}

	//set方法
	public void setAgisoAppSecret(String agisoAppSecret) {
		this.agisoAppSecret = agisoAppSecret;
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
