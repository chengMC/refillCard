package com.mc.refillCard.dto;

import lombok.Data;

import java.io.Serializable;

/****
 * @Author: MC
 * @Description:OriginalOrder构建
 * @Date 2021-3-21 14:41:57
 *****/
@Data
public class OriginalOrderDto implements Serializable{

	/**
	 * id
	 */
	private Long id;

	/**
	 * 购买数量
	 */
	private Long Num;

	/**
	 * 宝贝ID
	 */
	private Long NumIid;

	/**
	 * 子订单编号
	 */
	private Long Oid;

	/**
	 * 商家编码
	 */
	private String OuterIid;

	/**
	 * 商家商品sku编码
	 */
	private String OuterSkuId;

	/**
	 * 实付金额
	 */
	private String Payment;

	/**
	 * 价格（原价）
	 */
	private String Price;

	/**
	 * SKU属性名
	 */
	private String SkuPropertiesName;

	/**
	 * 宝贝标题
	 */
	private String Title;

	/**
	 * 付款时间
	 */
	private String PayTime;

	/**
	 * 商品金额
	 */
	private String TotalFee;
}
