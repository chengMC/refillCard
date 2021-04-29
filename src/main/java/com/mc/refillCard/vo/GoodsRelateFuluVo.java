package com.mc.refillCard.vo;

import lombok.Data;

import java.io.Serializable;
/****
 * @Author: MC
 * @Description:GoodsRelateFulu构建
 * @Date 2021-3-28 20:16:38
 *****/
@Data
public class GoodsRelateFuluVo implements Serializable{

	/**
	 * 
	 */
	private Long id;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 关联商品id
	 */
	private Long productId;

	/**
	 * 商品名称
	 */
	private String goodName;

	/**
	 * 商品id
	 */
	private Long goodId;

	/**
	 * 面值多少
	 */
	private String nominal;

	private String typeName;

	/**
	 * 备注
	 */
	private String remake;

	/**
	 * 商品类型 1:QB
	 */
	private Integer type;

	/**
	 * 启用状态，1启用，0删除 2 关闭
	 */
	private Integer status;



}
