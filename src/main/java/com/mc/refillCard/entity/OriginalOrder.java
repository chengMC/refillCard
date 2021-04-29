package com.mc.refillCard.entity;

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
	 *失败理由
	 */
	private String failReason;

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


}
