package com.mc.refillCard.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/****
 * @Author: MC
 * @Description:Role构建
 * @Date 2020-9-29 17:00:54
 *****/
@Data
public class RoleDto implements Serializable{

	private Long id;//主键

	private String roleName;//角色名称

	@NotBlank(message = "角色描述不能为空")
	private String description;//角色描述

	@NotBlank(message = "角色名称不能为空")
	private String nickName;//显示名称

	private Integer grade;//角色等级，按大小区分

	private Long[] menuId;//菜单id数组

}
