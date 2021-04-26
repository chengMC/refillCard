package com.mc.refillCard.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/****
 * @Author: MC
 * @Description:UserPricing构建
 * @Date 2021-4-24 21:40:05
 *****/
@Data
public class UserPricing implements Serializable{

	/**
	 * 
	 */
	private Long id;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 商品类型
	 */
	private Long goodTypeId;

	/**
	 * 商品名称
	 */
	private String goodTypeName;

	/**
	 * 统一价格
	 */
	private BigDecimal unifyPrice;

}
