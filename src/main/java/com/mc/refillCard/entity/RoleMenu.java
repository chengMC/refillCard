package com.mc.refillCard.entity;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author: MC
 * @Description:RoleMenu构建
 * @Date 2020-9-29 17:00:55
 *****/
public class RoleMenu implements Serializable{

	private Long id;//主键

	private Long roleId;//角色ID

	private Long menuId;//菜单ID

	private Date createTime;//创建时间



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
	public Long getMenuId() {
		return menuId;
	}

	//set方法
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	//get方法
	public Date getCreateTime() {
		return createTime;
	}

	//set方法
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}
