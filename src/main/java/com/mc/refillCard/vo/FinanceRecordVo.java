package com.mc.refillCard.vo;

import com.mc.refillCard.common.Enum.FinanceRecordTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/****
 * @Author: MC
 * @Description:FinanceRecord构建
 * @Date 2021-5-3 13:51:48
 *****/
public class FinanceRecordVo implements Serializable{

	/**
	 * 
	 */
	private Long id;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 交易前余额
	 */
	private BigDecimal transactionBefore;

	/**
	 * 交易后余额
	 */
	private BigDecimal transactionAfter;

	/**
	 * 交易金额
	 */
	private BigDecimal transactionMoney;

	/**
	 * 交易类型 1加款 2扣款
	 */
	private Integer transactionType;


	private String transactionTypeName ;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 启动状态 0 删除 1正常
	 */
	private Integer status;

	/**
	 * 创建人
	 */
	private String createEmp;

	private String userName;

	private String nickName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getTransactionBefore() {
		return transactionBefore;
	}

	public void setTransactionBefore(BigDecimal transactionBefore) {
		this.transactionBefore = transactionBefore;
	}

	public BigDecimal getTransactionAfter() {
		return transactionAfter;
	}

	public void setTransactionAfter(BigDecimal transactionAfter) {
		this.transactionAfter = transactionAfter;
	}

	public BigDecimal getTransactionMoney() {
		return transactionMoney;
	}

	public void setTransactionMoney(BigDecimal transactionMoney) {
		this.transactionMoney = transactionMoney;
	}

	public Integer getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionTypeName() {
		return FinanceRecordTypeEnum.getNameByCode(transactionType);
	}

	public void setTransactionTypeName(String transactionTypeName) {
		this.transactionTypeName = transactionTypeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateEmp() {
		return createEmp;
	}

	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
