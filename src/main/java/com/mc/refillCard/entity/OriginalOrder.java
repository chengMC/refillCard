package com.mc.refillCard.entity;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author: MC
 * @Description:OriginalOrder构建
 * @Date 2021-3-21 14:41:57
 *****/
public class OriginalOrder implements Serializable{

	/**
	 * id
	 */
	private Long id;

	/**
	 * 交易id
	 */
	private Long transactionId;

	/**
	 * 购买数量
	 */
	private Long num;

	/**
	 * 宝贝ID
	 */
	private Long numId;

	/**
	 * 子订单编号
	 */
	private Long oId;

	/**
	 * 商家编码
	 */
	private String outerId;

	/**
	 * 商家商品sku编码
	 */
	private String outerSkuId;

	/**
	 * 实付金额
	 */
	private String payment;

	/**
	 * 价格（原价）
	 */
	private String price;

	/**
	 * SKU属性名
	 */
	private String skuPropertiesName;

	/**
	 * 宝贝标题
	 */
	private String title;

	/**
	 * 付款时间
	 */
	private String payTime;

	/**
	 * 商品金额
	 */
	private String totalFee;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 启用状态，1启用，0删除 2 关闭
	 */
	private Integer orderStatus;

	/**
	 * 创建人
	 */
	private String createEmp;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 修改人
	 */
	private String updateEmp;



	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public Long getTransactionId() {
		return transactionId;
	}

	//set方法
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	//get方法
	public Long getNum() {
		return num;
	}

	//set方法
	public void setNum(Long num) {
		this.num = num;
	}
	//get方法
	public Long getNumId() {
		return numId;
	}

	//set方法
	public void setNumId(Long numId) {
		this.numId = numId;
	}
	//get方法
	public Long getOId() {
		return oId;
	}

	//set方法
	public void setOId(Long oId) {
		this.oId = oId;
	}
	//get方法
	public String getOuterId() {
		return outerId;
	}

	//set方法
	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}
	//get方法
	public String getOuterSkuId() {
		return outerSkuId;
	}

	//set方法
	public void setOuterSkuId(String outerSkuId) {
		this.outerSkuId = outerSkuId;
	}
	//get方法
	public String getPayment() {
		return payment;
	}

	//set方法
	public void setPayment(String payment) {
		this.payment = payment;
	}
	//get方法
	public String getPrice() {
		return price;
	}

	//set方法
	public void setPrice(String price) {
		this.price = price;
	}
	//get方法
	public String getSkuPropertiesName() {
		return skuPropertiesName;
	}

	//set方法
	public void setSkuPropertiesName(String skuPropertiesName) {
		this.skuPropertiesName = skuPropertiesName;
	}
	//get方法
	public String getTitle() {
		return title;
	}

	//set方法
	public void setTitle(String title) {
		this.title = title;
	}
	//get方法
	public String getPayTime() {
		return payTime;
	}

	//set方法
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	//get方法
	public String getTotalFee() {
		return totalFee;
	}

	//set方法
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	//get方法
	public String getRemark() {
		return remark;
	}

	//set方法
	public void setRemark(String remark) {
		this.remark = remark;
	}
	//get方法
	public Integer getOrderStatus() {
		return orderStatus;
	}

	//set方法
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
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
	//get方法
	public Date getUpdateTime() {
		return updateTime;
	}

	//set方法
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	//get方法
	public String getUpdateEmp() {
		return updateEmp;
	}

	//set方法
	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}


}
