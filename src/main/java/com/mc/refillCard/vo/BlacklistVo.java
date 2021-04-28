package com.mc.refillCard.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/****
 * @Author: MC
 * @Description:Blacklist构建
 * @Date 2021-4-7 21:03:32
 *****/
@Data
public class BlacklistVo implements Serializable{

	/**
	 * 
	 */
	private Long id;

	/**
	 * 商品类别id
	 */
	private Long goodTypeId;

	/**
	 * 商品类别名称
	 */
	private String goodTypeName;

	/**
	 * 账号
	 */
	private String account;

	/**
	 * 用户
	 */
	private Long userId;

	/**
	 * 启动状态 0 禁用 1正常
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
