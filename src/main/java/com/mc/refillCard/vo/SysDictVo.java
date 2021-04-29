package com.mc.refillCard.vo;

import lombok.Data;

import java.io.Serializable;

/****
 * @Author: MC
 * @Description:SysDict构建
 * @Date 2021-1-26 17:11:36
 *****/
@Data
public class SysDictVo implements Serializable{


	/**
	 * id
	 */
	private Long id;


	/**
	 *  数据编码 
	 */
	private String dataCode;


	/**
	 *  数据名称/值 
	 */
	private String dataValue;

	/**
	 * 数据描述
	 */
	private String dataDesc;

}
