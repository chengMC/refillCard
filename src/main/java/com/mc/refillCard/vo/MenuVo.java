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
public class MenuVo implements Serializable{

	private Long id;//主键

	private String menuName;//菜单名称

	private Long parentId;//父菜单主键id

	private String displayName;//显示名称

	private Integer displayOrder;//显示顺序

	private String url;//菜单URL

	private String imgUrl;//菜单图标

	private Long stationId;//仅菜单中列表显示使用

	private String mark;//标记

	private List<MenuVo> children;



}
