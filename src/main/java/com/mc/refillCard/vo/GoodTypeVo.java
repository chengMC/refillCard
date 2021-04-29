package com.mc.refillCard.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author: MC
 * @Description:GoodType构建
 * @Date 2021-4-7 21:05:20
 *****/
@Data
public class GoodTypeVo implements Serializable{

	/**
	 *
	 */
	private Long id;

	/**
	 * 商品类别名称
	 */
	private String typeName;

	/**
	 * 启动状态 0 删除 1正常
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;

}
