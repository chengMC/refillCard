package com.mc.refillCard.vo;

import lombok.Data;

import java.io.Serializable;
/****
 * @Author: MC
 * @Description:GoodType构建
 * @Date 2021-4-7 21:05:20
 *****/
@Data
public class GoodTypeEnumVo implements Serializable{

	/**
	 * 
	 */
	private Long id;

	/**
	 * 商品类别名称
	 */
	private String typeName;

}
