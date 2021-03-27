package com.mc.refillCard.entity;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author: MC
 * @Description:SysDict构建
 * @Date 2021-1-26 17:11:36
 *****/
public class SysDict implements Serializable{


	/**
	 * id
	 */
	private Long id;


	/**
	 *  父ID 
	 */
	private Integer parentId;


	/**
	 *  数据编码 
	 */
	private String dataCode;


	/**
	 *  数据名称/值 
	 */
	private String dataValue;


	/**
	 *  顺序 
	 */
	private Integer sortNo;


	/**
	 * 0正常,1删除
	 */
	private Integer status;


	/**
	 * 数据描述
	 */
	private String dataDesc;


	/**
	 * 创建时间
	 */
	private Date createTime;


	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 端口
	 */
	private String port;

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public Integer getParentId() {
		return parentId;
	}

	//set方法
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	//get方法
	public String getDataCode() {
		return dataCode;
	}

	//set方法
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
	//get方法
	public String getDataValue() {
		return dataValue;
	}

	//set方法
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	//get方法
	public Integer getSortNo() {
		return sortNo;
	}

	//set方法
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
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
	public String getDataDesc() {
		return dataDesc;
	}

	//set方法
	public void setDataDesc(String dataDesc) {
		this.dataDesc = dataDesc;
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
