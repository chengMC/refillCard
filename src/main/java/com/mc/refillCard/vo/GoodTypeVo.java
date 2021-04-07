package com.mc.refillCard.vo;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;
/****
 * @Author: MC
 * @Description:GoodType构建
 * @Date 2021-4-7 21:05:20
 *****/
public class GoodTypeVo implements Serializable{

	/**
	 * 
	 */
	private Long id;

	/**
	 * 商品类别名称
	 */
	private String typeName;

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
	public String getTypeName() {
		return typeName;
	}

	//set方法
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
