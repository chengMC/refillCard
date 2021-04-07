package com.mc.refillCard.dto;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;
/****
 * @Author: MC
 * @Description:Blacklist构建
 * @Date 2021-4-7 21:03:32
 *****/
public class BlacklistDto implements Serializable{

	/**
	 * 
	 */
	private Long id;

	/**
	 * 商品类别id
	 */
	private Long goodTypeId;

	/**
	 * 商品类别名称
	 */
	private String goodTypeName;

	/**
	 * 用户
	 */
	private Long userId;

	/**
	 * 账号
	 */
	private String account;

	/**
	 * 启动状态 0 删除 1正常
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;



	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public Long getGoodTypeId() {
		return goodTypeId;
	}

	//set方法
	public void setGoodTypeId(Long goodTypeId) {
		this.goodTypeId = goodTypeId;
	}
	//get方法
	public String getGoodTypeName() {
		return goodTypeName;
	}

	//set方法
	public void setGoodTypeName(String goodTypeName) {
		this.goodTypeName = goodTypeName;
	}
	//get方法
	public String getAccount() {
		return account;
	}

	//set方法
	public void setAccount(String account) {
		this.account = account;
	}
	//get方法
	public Integer getStatus() {
		return status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	//set方法
	public void setStatus(Integer status) {
		this.status = status;
	}
	//get方法
	public Date getCreateTime() {
		return createTime;
	}

	//set方法
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	//get方法
	public Date getUpdateTime() {
		return updateTime;
	}

	//set方法
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


}
