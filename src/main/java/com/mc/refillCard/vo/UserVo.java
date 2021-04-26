package com.mc.refillCard.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/****
 * @Author: MC
 * @Description:User构建
 * @Date 2020-8-13 10:55:54
 *****/
@Data
public class UserVo implements Serializable{

	private Long id;//id

	private String userName;//用户名

	private String phone;//电话

	private String token;

	private String logo;//头像

	private String email;//邮箱

	private Integer status;//用户启用状态，1启用，0删除 2 关闭

	private String roleId;//角色id

	private String roleName;//角色名称

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;//创建时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updateTime;//修改时间

	/**
	 * 部门id
	 */
	private Long departmentId;

	/**
	 * 部门名称
	 */
	private String departmentName;

	/***
	 * 余额
	 */
	private BigDecimal balance;

}
