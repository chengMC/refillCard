package com.mc.refillCard.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author: MC
 * @Description:Log构建
 * @Date 2020-5-29 9:55:47
 *****/
public class LogInformationVo implements Serializable{

	private Long id;//主键

	private String userName;//当前登录人

	private Long userId;//登录人ID

	private String action;//访问页面

	private String operation;//操作详情

	private String result;//操作结果

	private String ip;//登录IP

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;//创建时间

	private Long departmentId;//部门id

	private String departmentName;//部门名称

	private Long roleId;//角色id

	private String roleName;//角色i名称

	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public String getUserName() {
		return userName;
	}

	//set方法
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getAction() {
		return action;
	}

	//set方法
	public void setAction(String action) {
		this.action = action;
	}
	//get方法
	public String getOperation() {
		return operation;
	}

	//set方法
	public void setOperation(String operation) {
		this.operation = operation;
	}
	//get方法
	public String getResult() {
		return result;
	}

	//set方法
	public void setResult(String result) {
		this.result = result;
	}
	//get方法
	public String getIp() {
		return ip;
	}

	//set方法
	public void setIp(String ip) {
		this.ip = ip;
	}
	//get方法
	public Date getCreateTime() {
		return createTime;
	}

	//set方法
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
