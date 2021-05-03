package com.mc.refillCard.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/****
 * @Author: MC
 * @Description:OriginalOrder构建
 * @Date 2021-3-21 14:41:57
 *****/
@Data
public class OriginalOrderVo implements Serializable{
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
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long oId;

	/**
	 * 实付金额
	 */
	private String payment;
	/**
	 * 付款时间
	 */
	private String payTime;

	/**
	 * 价格（原价）
	 */
	private String price;
	/**
	 * 宝贝标题
	 */
	private String title;

	/**
	 * 商品金额
	 */
	private String totalFee;

	/**
	 *扣款前余额
	 */
	private BigDecimal beforeBalance;
	/**
	 *扣款后余额
	 */
	private BigDecimal afterBalance;
	/**
	 *扣款金额
	 */
	private BigDecimal deductPrice;
	/**
	 * 供应商
	 */
	private String supplier;
	/**
	 *外部订单号
	 */
	private String externalOrderId;
	/**
	 * 充值账号
	 */
	private String chargeAccount;

	//买家备注
	private String receiverAddress;

	//买家地区
	private String buyerArea;

	/**
	 *失败理由
	 */
	private String failReason;

	/**
	 * 订单状态，1待推送，2推送成功  3推送失败
	 */
	private Integer orderStatus;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 商品类型
	 */
	private Integer type;

	/**
	 * 商品类型名称
	 */
	private String typeName;

	/**
	 * 卖家昵称
	 */
	private String sellerNick;

	/**
	 * 买家昵称
	 */
	private String buyerNick;

}
