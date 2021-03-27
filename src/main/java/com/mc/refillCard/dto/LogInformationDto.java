package com.mc.refillCard.dto;

import java.io.Serializable;

/****
 * @Author: MC
 * @Description:Log构建
 * @Date 2020-5-29 9:55:47
 *****/
public class LogInformationDto implements Serializable{

	//用户名
	private String userName;

	//查询开始时间
	private String startTime;

	//查询结束时间
	private String endTime;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
