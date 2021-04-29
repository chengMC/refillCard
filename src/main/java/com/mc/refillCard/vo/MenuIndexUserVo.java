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
public class MenuIndexUserVo implements Serializable{

	private Long id;//主键

	private String title;//菜单名称

	private Long parentId;//父菜单主键id

	private String index;//菜单URL

	private String icon;//菜单图标

	private List<MenuIndexUserVo> subs;

}
