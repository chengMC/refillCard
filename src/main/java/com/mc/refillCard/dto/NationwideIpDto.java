package com.mc.refillCard.dto;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;
/****
 * @Author: MC
 * @Description:NationwideIp构建
 * @Date 2021-4-3 16:33:59
 *****/
public class NationwideIpDto implements Serializable{

	/**
	 * 
	 */
	private Long id;

	/**
	 * 开始IP段
	 */
	private String startIp;

	/**
	 * 结束IP段
	 */
	private String endIp;

	/**
	 * 区域名字
	 */
	private String area;

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
	public String getStartIp() {
		return startIp;
	}

	//set方法
	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}
	//get方法
	public String getEndIp() {
		return endIp;
	}

	//set方法
	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}
	//get方法
	public String getArea() {
		return area;
	}

	//set方法
	public void setArea(String area) {
		this.area = area;
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
