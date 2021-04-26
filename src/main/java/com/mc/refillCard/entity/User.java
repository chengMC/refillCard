package com.mc.refillCard.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/****
 * @Author: MC
 * @Description:User构建
 * @Date 2020-11-19 14:11:56
 *****/
@Data
public class User implements Serializable{

	private Long id;//id

	private String userName;//用户名

	private String validUserName;//用户名验证

	private String password;//用户密码

	private String phone;//手机号

	private String remark;//备注

	private Integer status;//用户启用状态，1启用，0删除 2 关闭

	private String createEmp;//创建人

	private Date createTime;//创建时间

	private Date updateTime;//修改时间

	private String updateEmp;//修改人

	private String logo="";//LOGO

	private String email="";//邮箱

	private String roleName; //角色名称

	private Long roleId;//角色id

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

	private Integer isHidden;

}
