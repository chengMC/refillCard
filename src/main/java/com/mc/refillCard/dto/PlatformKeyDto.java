package com.mc.refillCard.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author: MC
 * @Description:PlatformKey构建
 * @Date 2021-5-4 22:30:48
 *****/
@Data
public class PlatformKeyDto implements Serializable{

	/**
	 * 
	 */
	private Long id;

	/**
	 * 福禄key
	 */
	private String appKey;

	/**
	 * 福禄秘钥
	 */
	private String appSercret;

	/**
	 * 福利账号名称
	 */
	private String appName;

	/**
	 * 账号类型
	 */
	private Integer appType;

	/**
	 * 排序
	 */
	private Integer orderBy;

	/**
	 * 启动状态 0 不启用 1 启用
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;


//
//
//
//	//get方法
//	public Long getId() {
//		return id;
//	}
//
//	//set方法
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	//get方法
//	public String getAppKey() {
//		return appKey;
//	}
//
//	//set方法
//	public void setAppKey(String appKey) {
//		this.appKey = appKey;
//	}
//
//	//get方法
//	public String getAppSercret() {
//		return appSercret;
//	}
//
//	//set方法
//	public void setAppSercret(String appSercret) {
//		this.appSercret = appSercret;
//	}
//
//	//get方法
//	public String getAppName() {
//		return appName;
//	}
//
//	//set方法
//	public void setAppName(String appName) {
//		this.appName = appName;
//	}
//
//	//get方法
//	public Integer getAppType() {
//		return appType;
//	}
//
//	//set方法
//	public void setAppType(Integer appType) {
//		this.appType = appType;
//	}
//
//	//get方法
//	public Integer getOrderBy() {
//		return orderBy;
//	}
//
//	//set方法
//	public void setOrderBy(Integer orderBy) {
//		this.orderBy = orderBy;
//	}
//
//	//get方法
//	public Integer getStatus() {
//		return status;
//	}
//
//	//set方法
//	public void setStatus(Integer status) {
//		this.status = status;
//	}
//
//	//get方法
//	public Date getCreateTime() {
//		return createTime;
//	}
//
//	//set方法
//	public void setCreateTime(Date createTime) {
//		this.createTime = createTime;
//	}
//


}
