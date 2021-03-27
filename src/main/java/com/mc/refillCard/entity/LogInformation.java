package com.mc.refillCard.entity;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author: MC
 * @Description:Log构建
 * @Date 2020-5-29 9:55:47
 *****/
public class LogInformation implements Serializable{

	private Long id;//主键

	private String userName;//当前登录人

	private Long userId;//登录人ID

	private Long companyId;//所属公司ID

	private String action;//访问页面

	private String operation;//操作详情

	private String result;//操作结果

	private String ip;//登录IP

	private Date createTime;//创建时间

	//*******************************

	private Date startTime;//查询开始时间

	private Date endTime;//查询结束时间

	private String[] searchTime;//查询时间段


	private int num;




	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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
	public Long getCompanyId() {
		return companyId;
	}

	//set方法
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String[] getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(String[] searchTime) {
		this.searchTime = searchTime;
	}
}
