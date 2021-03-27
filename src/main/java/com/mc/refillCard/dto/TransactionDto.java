package com.mc.refillCard.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/****
 * @Author: MC
 * @Description:Transaction构建
 * @Date 2021-3-20 20:24:42
 *****/
@Data
public class TransactionDto implements Serializable{

	/**
	 * id
	 */
	private Long id;

	/**
	 * 平台
	 */
	private String Platform;

	/**
	 * 平台用户id
	 */
	private String PlatformUserId;

	/**
	 * 交易id
	 */
	private String Tid;

	/**
	 * 推送状态
	 */
	private String Status;

	/**
	 * 卖家霓裳
	 */
	private String SellerNick;

	/**
	 * 卖家昵称
	 */
	private String BuyerNick;

	/**
	 * 卖家信息
	 */
	private String BuyerMessage;

	/**
	 * 价格
	 */
	private String Price;

	/**
	 * 数量
	 */
	private Long Num;

	/**
	 * 总价
	 */
	private String TotalFee;

	/**
	 * 付款金额
	 */
	private String Payment;

	/**
	 * 付款时间
	 */
	private String PayTime;

	/**
	 * 交易创建时间
	 */
	private String Created;

	/**
	 * 接收人
	 */
	private String ReceiverName;

	/**
	 * 接收人手机号
	 */
	private String ReceiverMobile;

	/**
	 * 接收人电话
	 */
	private String ReceiverPhone;

	/**
	 * 接收人地址
	 */
	private String ReceiverAddress;

	/**
	 * 买方区域
	 */
	private String BuyerArea;

	/**
	 * 扩展字段
	 */
	private String ExtendedFields;

	/**
	 * 卖方手机号
	 */
	private String SellerMemo;

	/**
	 * 卖方类型
	 */
	private String SellerFlag;

	/**
	 * 信用卡费用
	 */
	private String CreditCardFee;

	private List<OriginalOrderDto> Orders;

}
