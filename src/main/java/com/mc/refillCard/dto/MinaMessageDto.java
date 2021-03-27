package com.mc.refillCard.dto;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author: MC
 * @Description:Toilet构建
 * @Date 2021-3-2 14:21:25
 *****/
public class MinaMessageDto implements Serializable{

	/**
	 * 主键
	 */
	private String code;

	/**
	 * 设备地址码
	 */
	private String deviceAddress;

	/**
	 *  校验码
	 */
	private String func;
	/**
	 *  温度
	 */
	private String temperature;
	/**
	 *  湿度
	 */
	private String humidity;
	/**
	 *  氨气
	 */
	private String ammoniaGas;
	/**
	 *  硫化氢
	 */
	private String ydrothion;

	/**
	 * 公厕id
	 */
	private String toiletId;

	/***
	 *  时间
	 */
	private Date time;

	/**
	 * 类型  1男厕  2 女厕
	 */
	private Integer deviceType;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDeviceAddress() {
		return deviceAddress;
	}

	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getAmmoniaGas() {
		return ammoniaGas;
	}

	public void setAmmoniaGas(String ammoniaGas) {
		this.ammoniaGas = ammoniaGas;
	}

	public String getYdrothion() {
		return ydrothion;
	}

	public void setYdrothion(String ydrothion) {
		this.ydrothion = ydrothion;
	}

	public String getToiletId() {
		return toiletId;
	}

	public void setToiletId(String toiletId) {
		this.toiletId = toiletId;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
