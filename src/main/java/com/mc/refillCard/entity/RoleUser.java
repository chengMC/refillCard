package com.mc.refillCard.entity;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author: MC
 * @Description:RoleUser构建
 * @Date 2020-9-29 17:00:55
 *****/
public class RoleUser implements Serializable{

	private Long id;//主键

	private Long roleId;//角色ID

	private Long userId;//用户ID

	private Date createTime;//创建时间

	private Long status;//0:启用 1:禁用



	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public Long getRoleId() {
		return roleId;
	}

	//set方法
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	//get方法
	public Long getUserId() {
		return userId;
	}

	//set方法
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Long getStatus() {
		return status;
	}

	//set方法
	public void setStatus(Long status) {
		this.status = status;
	}


}
