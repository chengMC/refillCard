package com.mc.refillCard.vo;

import java.io.Serializable;

/****
 * @Author: MC
 * @Description:GoodsRelate构建
 * @Date 2021-3-21 19:38:28
 *****/
public class GoodsRelateVo implements Serializable{

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
	 * 关联商品名称
	 */
	private String productName;

	/**
	 * 关联商品模板Id
	 */
	private String templateId;

	/**
	 * 对接平台 1 福禄
	 */
	private Integer platform;

	/**
	 * 商品id
	 */
	private Long goodId;

	/**
	 * 面值多少
	 */
	private String nominal;

	/**
	 * 备注
	 */
	private String remake;

	/**
	 * 商品类型 1:QB
	 */
	private Integer type;



	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
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
	public Long getProductId() {
		return productId;
	}

	//set方法
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	//get方法
	public String getProductName() {
		return productName;
	}

	//set方法
	public void setProductName(String productName) {
		this.productName = productName;
	}
	//get方法
	public String getTemplateId() {
		return templateId;
	}

	//set方法
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	//get方法
	public Integer getPlatform() {
		return platform;
	}

	//set方法
	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
	//get方法
	public Long getGoodId() {
		return goodId;
	}

	//set方法
	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}
	//get方法
	public String getNominal() {
		return nominal;
	}

	//set方法
	public void setNominal(String nominal) {
		this.nominal = nominal;
	}
	//get方法
	public String getRemake() {
		return remake;
	}

	//set方法
	public void setRemake(String remake) {
		this.remake = remake;
	}
	//get方法
	public Integer getType() {
		return type;
	}

	//set方法
	public void setType(Integer type) {
		this.type = type;
	}


}
