package com.mc.refillCard.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/****
 * @Author: MC
 * @Description:FinanceRecord构建
 * @Date 2021-5-3 13:51:48
 *****/
@Data
public class FinanceRecord implements Serializable{

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

	/**
	 * 创建时间
	 */
	private Date createTime;


}
