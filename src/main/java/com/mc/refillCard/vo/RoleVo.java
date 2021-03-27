package com.mc.refillCard.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author: MC
 * @Description:Role构建
 * @Date 2020-9-29 17:00:54
 *****/
@Data
public class RoleVo implements Serializable{

	private Long id;//主键

	private String description;//角色描述

	private String nickName;//显示名称

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;//创建时间

	private Long[] menuId;//菜单id数组
}
