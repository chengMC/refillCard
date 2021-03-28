package com.mc.refillCard.dto;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;
/****
 * @Author: MC
 * @Description:Goods构建
 * @Date 2021-3-28 20:28:52
 *****/
public class GoodsDto implements Serializable{

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
	 * 关联商品模板Id
	 */
	private String templateId;

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
	 * 启用状态，1启用，0删除 2 关闭
	 */
	private Integer status;

	/**
	 * 创建人
	 */
	private String createEmp;

	/**
	 * 创建时间
	 */
	private Date createTime;



	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
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
	public String getArea() {
		return area;
	}

	//set方法
	public void setArea(String area) {
		this.area = area;
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
	//get方法
	public String getCreateEmp() {
		return createEmp;
	}

	//set方法
	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}
	//get方法
	public Date getCreateTime() {
		return createTime;
	}

	//set方法
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}
