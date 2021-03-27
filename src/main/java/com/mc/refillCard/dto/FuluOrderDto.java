package com.mc.refillCard.dto;

import lombok.Data;

import java.io.Serializable;

/****
 * @Author: MC
 * @Description:OriginalOrder构建
 * @Date 2021-3-21 14:41:57
 *****/
@Data
public class FuluOrderDto implements Serializable{

	/**
	 * 订单编号
	 */
	private String order_id;

	/**
	 * 外部订单号，每次请求必须唯一
	 */
	private Long product_id;

	/**
	 * 商品Id
	 */
	private String product_name;

	/**
	 * 商品名称
	 */
	private String charge_account;

	/**
	 * 充值账号
	 */
	private String customer_order_no;

	/**
	 * 创建时间
	 */
	private String create_time;

	/**
	 * 购买数量
	 */
	private Integer buy_num;

	/**
	 * 交易单价（单位：元）
	 */
	private Integer order_price;

	/**
	 * 订单状态： （success：成功，processing：处理中，failed：失败，untreated：未处理）
	 */
	private String order_state;

	/**
	 * 交易单价（单位：元）
	 */
	private String finish_time;

	/**
	 * 充值区（中文）,仅网游直充订单返回
	 */
	private String area;

	/**
	 * 充值服（中文）,仅网游直充订单返回
	 */
	private String server;

	/**
	 *计费方式（中文）,仅网游直充订单返回
	 */
	private String type;

	/**
	 * 卡密信息
	 */
	private FuluOrderCardsDto cards;

	/**
	 * 订单类型：1-话费 2-流量 3-卡密 4-直充
	 */
	private String order_type;

	/**
	 * 运营商流水号
	 */
	private String operator_serial_number;

}
