package com.mc.refillCard.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/****
 * @Author: MC
 * @Description:Menu构建
 * @Date 2020-9-29 17:00:54
 *****/
@Data
public class MenuIndexVo implements Serializable{

	private Long id;//主键

	private String moduleName;//菜单名称

	private Long parentId;//父菜单主键id

	private String routeName;//菜单URL

	private String icon;//菜单图标

	private String checked;//前端事件

	private String uncheck;//前端事件

	private List<MenuIndexVo> children;

}
