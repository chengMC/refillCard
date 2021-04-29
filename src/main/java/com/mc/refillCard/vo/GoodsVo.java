package com.mc.refillCard.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/****
 * @Author: MC
 * @Description:Goods构建
 * @Date 2021-3-28 20:28:52
 *****/
@Data
public class GoodsVo implements Serializable{

	/**
	 * 
	 */
	private Long id;

	/**
	 * 关联商品id
	 */
	private Long productId;

	/**
	 * 关联商品名称
	 */
	private String productName;

	/**
	 * 对接平台 1 福禄
	 */
	private Integer platform;

	/**
	 * 地区
	 */
	private String area;

	/**
	 * 备注
	 */
	private String remake;

	/**
	 * 商品类型 1:QB
	 */
	private Integer type;

	/**
	 * 启用状态，1启用，0禁用
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;


}
