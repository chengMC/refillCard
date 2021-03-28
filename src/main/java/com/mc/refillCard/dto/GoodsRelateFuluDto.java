package com.mc.refillCard.dto;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
/****
 * @Author: MC
 * @Description:GoodsRelateFulu构建
 * @Date 2021-3-28 20:16:38
 *****/
public class GoodsRelateFuluDto implements Serializable{

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
	public String getGoodName() {
		return goodName;
	}

	//set方法
	public void setGoodName(String goodName) {
		this.goodName = goodName;
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
	//get方法
	public Integer getStatus() {
		return status;
	}

	//set方法
	public void setStatus(Integer status) {
		this.status = status;
	}


}
