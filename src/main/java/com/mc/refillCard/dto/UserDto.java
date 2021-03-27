package com.mc.refillCard.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author: MC
 * @Description:User构建
 * @Date 2020-8-13 10:55:54
 *****/
public class UserDto implements Serializable{

	private Long id;//id

	@NotBlank(message = "用户名称不能为空")
	private String userName;//用户名

	private String password;//用户密码

	@NotBlank(message = "联系方式不能为空")
	private String phone;//手机号

	private String remark;//备注

	private Integer status;//用户启用状态，1启用，0删除 2 关闭

	private String createEmp;//创建人

	private Date createTime;//创建时间

	private Date updateTime;//修改时间

	private String updateEmp;//修改人

	private String logo;//LOGO

	private String email;//邮箱

	@NotBlank(message = "角色名称不能为空")
	private String roleName; //角色名称
	@NotNull(message = "角色名称不能为空")
	private Long roleId;//角色id

	/**
	 * 部门id
	 */
	private Long departmentId;

	/**
	 * 部门名称
	 */
	private String departmentName;


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
	public String getPassword() {
		return password;
	}

	//set方法
	public void setPassword(String password) {
		this.password = password;
	}
	//get方法
	public String getPhone() {
		return phone;
	}

	//set方法
	public void setPhone(String phone) {
		this.phone = phone;
	}
	//get方法
	public String getRemark() {
		return remark;
	}

	//set方法
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getCreateEmp() {
		return createEmp;
	}

	//set方法
	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
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
	//get方法
	public String getUpdateEmp() {
		return updateEmp;
	}

	//set方法
	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}
	//get方法
	public String getLogo() {
		return logo;
	}

	//set方法
	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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
}
