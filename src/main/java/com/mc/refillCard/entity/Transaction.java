package com.mc.refillCard.entity;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author: MC
 * @Description:Transaction构建
 * @Date 2021-3-20 21:17:23
 *****/
public class Transaction implements Serializable{

	/**
	 * id
	 */
	private Long id;

	/**
	 * 平台
	 */
	private String platForm;

	/**
	 * 平台用户id
	 */
	private String platformUserId;

	/**
	 * 交易id
	 */
	private String tid;

	/**
	 * 推送状态
	 */
	private String status;

	/**
	 * 卖家霓裳
	 */
	private String sellerNick;

	/**
	 * 卖家昵称
	 */
	private String buyerNick;

	/**
	 * 卖家信息
	 */
	private String buyerMessage;

	/**
	 * 价格
	 */
	private String price;

	/**
	 * 数量
	 */
	private Long num;

	/**
	 * 总价
	 */
	private String totalFee;

	/**
	 * 付款金额
	 */
	private String payment;

	/**
	 * 付款时间
	 */
	private String payTime;

	/**
	 * 交易创建时间
	 */
	private String created;

	/**
	 * 接收人
	 */
	private String receiverName;

	/**
	 * 接收人手机号
	 */
	private String receiverMobile;

	/**
	 * 接收人电话
	 */
	private String receiverPhone;

	/**
	 * 接收人地址
	 */
	private String receiverAddress;

	/**
	 * 买方区域
	 */
	private String buyerArea;

	/**
	 * 扩展字段
	 */
	private String extendedFields;

	/**
	 * 卖方手机号
	 */
	private String sellerMemo;

	/**
	 * 卖方类型
	 */
	private String sellerFlag;

	/**
	 * 信用卡费用
	 */
	private String creditCardFee;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 状态，0删除 1待推送 2 推送成功 3推送失败
	 */
	private Integer state;

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
	public String getPlatForm() {
		return platForm;
	}

	//set方法
	public void setPlatForm(String platForm) {
		this.platForm = platForm;
	}
	//get方法
	public String getPlatformUserId() {
		return platformUserId;
	}

	//set方法
	public void setPlatformUserId(String platformUserId) {
		this.platformUserId = platformUserId;
	}
	//get方法
	public String getTid() {
		return tid;
	}

	//set方法
	public void setTid(String tid) {
		this.tid = tid;
	}
	//get方法
	public String getStatus() {
		return status;
	}

	//set方法
	public void setStatus(String status) {
		this.status = status;
	}
	//get方法
	public String getSellerNick() {
		return sellerNick;
	}

	//set方法
	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}
	//get方法
	public String getBuyerNick() {
		return buyerNick;
	}

	//set方法
	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}
	//get方法
	public String getBuyerMessage() {
		return buyerMessage;
	}

	//set方法
	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
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
	public Long getNum() {
		return num;
	}

	//set方法
	public void setNum(Long num) {
		this.num = num;
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
	public String getPayment() {
		return payment;
	}

	//set方法
	public void setPayment(String payment) {
		this.payment = payment;
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
	public String getCreated() {
		return created;
	}

	//set方法
	public void setCreated(String created) {
		this.created = created;
	}
	//get方法
	public String getReceiverName() {
		return receiverName;
	}

	//set方法
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	//get方法
	public String getReceiverMobile() {
		return receiverMobile;
	}

	//set方法
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	//get方法
	public String getReceiverPhone() {
		return receiverPhone;
	}

	//set方法
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	//get方法
	public String getReceiverAddress() {
		return receiverAddress;
	}

	//set方法
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	//get方法
	public String getBuyerArea() {
		return buyerArea;
	}

	//set方法
	public void setBuyerArea(String buyerArea) {
		this.buyerArea = buyerArea;
	}
	//get方法
	public String getExtendedFields() {
		return extendedFields;
	}

	//set方法
	public void setExtendedFields(String extendedFields) {
		this.extendedFields = extendedFields;
	}
	//get方法
	public String getSellerMemo() {
		return sellerMemo;
	}

	//set方法
	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}
	//get方法
	public String getSellerFlag() {
		return sellerFlag;
	}

	//set方法
	public void setSellerFlag(String sellerFlag) {
		this.sellerFlag = sellerFlag;
	}
	//get方法
	public String getCreditCardFee() {
		return creditCardFee;
	}

	//set方法
	public void setCreditCardFee(String creditCardFee) {
		this.creditCardFee = creditCardFee;
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
	public Integer getState() {
		return state;
	}

	//set方法
	public void setState(Integer state) {
		this.state = state;
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
