package com.mc.refillCard.vo;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author: MC
 * @Description:GameServer构建
 * @Date 2021-5-10 23:11:50
 *****/
public class GameServerVo implements Serializable{

	/**
	 * 
	 */
	private Long id;

	/**
	 * 区域值
	 */
	private String areaValue;

	/**
	 * 区域名称
	 */
	private String areaName;

	/**
	 * 区域名称加运营商
	 */
	private String areaNameOperator;

	/**
	 * 商品类型id
	 */
	private Long goodTypeId;

	/**
	 * 商品类型名称
	 */
	private String goodTypeName;

	/**
	 * 备注
	 */
	private String remake;

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
	public String getAreaValue() {
		return areaValue;
	}

	//set方法
	public void setAreaValue(String areaValue) {
		this.areaValue = areaValue;
	}
	//get方法
	public String getAreaName() {
		return areaName;
	}

	//set方法
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	//get方法
	public String getAreaNameOperator() {
		return areaNameOperator;
	}

	//set方法
	public void setAreaNameOperator(String areaNameOperator) {
		this.areaNameOperator = areaNameOperator;
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
	public String getRemake() {
		return remake;
	}

	//set方法
	public void setRemake(String remake) {
		this.remake = remake;
	}
	//get方法
	public Integer getStatus() {
		return status;
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
