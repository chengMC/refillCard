package com.mc.refillCard.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/****
 * 话费实体
 * @Author: MC
 * @Description:Blacklist构建
 * @Date 2021-4-7 21:03:32
 *****/
@Data
public class PhoneBillDto implements Serializable{

	/**
	 * 客户ID
	 */
	@NotNull(message = "客户ID不能为空")
	private String szAgentId;

	/**
	 * 订单编号
	 */
	private String szOrderId;

	/**
	 * 充值号码
	 */
	@NotNull(message = "充值号码不能为空")
	private String szPhoneNum;

	/**
	 * 充值金额
	 */
	@NotNull(message = "充值金额不能为空")
	private Integer nMoney;

	/**
	 * 运营商编码
	 */
	private Integer nSortType;

	private Integer nProductClass =1;

	private Integer nProductType =1;

	/**
	 * 产品编码 常规话费不需要填写
	 */
	private String szProductId;

	/**
	 * 时间戳。
	 */
	@NotNull(message = "时间戳不能为空")
	private Date szTimeStamp;

	/**
	 * 验证摘要串
	 */
	@NotNull(message = "验证摘要串不能为空")
	private String szVerifyString;

	/**
	 * 完成结果回调通知地址
	 */
	private String szNotifyUrl;

	/**
	 * 结果返回格式:
	 * JSON（默认）
	 * XML
	 */
	private String szFormat;

	/**
	 * 非话费流量充值用户手机号,中石油必传,QQ会员提供QQ号
	 */
	private String thirdphone;
	/**
	 * json字符串，特定产品必传，如Q币订单需传ip，中石油必传姓名cardName、身份证号码cardId，
	 * 账号类型numtype(手机号(默认可不传)：phone;QQ号码：qq,微信号码：wx)例：
	 * {"cardName":"张三","cardId":"xxxxx","numtype":"phone"}
	 */
	private String params;

}
