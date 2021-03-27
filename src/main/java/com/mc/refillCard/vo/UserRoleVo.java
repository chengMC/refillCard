package com.mc.refillCard.vo;

import java.io.Serializable;

/****
 * @Author: MC
 * @Description:User构建
 * @Date 2020-8-13 10:55:54
 *****/

public class UserRoleVo implements Serializable{

	private Long id;//id

	private String userName;//用户名

	private String phone;//电话

	private Long identityStatus;//身份状态 0 :个人 1：企业

	private Long companyId;//公司id

	private String companyName;//公司名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getIdentityStatus() {
		return identityStatus;
	}

	public void setIdentityStatus(Long identityStatus) {
		this.identityStatus = identityStatus;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
