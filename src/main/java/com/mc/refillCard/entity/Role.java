package com.mc.refillCard.entity;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author: MC
 * @Description:Role构建
 * @Date 2020-9-29 17:00:54
 *****/
public class Role implements Serializable{

	private Long id;//主键

	private String roleName;//角色名称

	private String description;//角色描述

	private String nickName;//显示名称

	private Integer grade;//角色等级，按大小区分

	private Date createTime;//创建时间

	private Long companyId;//公司ID

	/**
	 * 是否隐藏 0 隐藏 1 显示
	 */
	private Integer isHidden;

	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public String getRoleName() {
		return roleName;
	}

	//set方法
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	//get方法
	public String getDescription() {
		return description;
	}

	//set方法
	public void setDescription(String description) {
		this.description = description;
	}
	//get方法
	public String getNickName() {
		return nickName;
	}

	//set方法
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	//get方法
	public Integer getGrade() {
		return grade;
	}

	//set方法
	public void setGrade(Integer grade) {
		this.grade = grade;
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
	public Long getCompanyId() {
		return companyId;
	}

	//set方法
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Integer isHidden) {
		this.isHidden = isHidden;
	}
}
