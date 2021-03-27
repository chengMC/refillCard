package com.mc.refillCard.entity;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author: MC
 * @Description:Menu构建
 * @Date 2020-9-29 17:00:54
 *****/
public class Menu implements Serializable{

	private Long id;//主键

	private String menuName;//菜单名称

	private Long parentId;//父菜单主键id

	private String displayName;//显示名称

	private Integer displayOrder;//显示顺序

	private String url;//菜单URL

	private Integer state;//菜单状态

	private String imgUrl;//菜单图标

	private Long stationId;//仅菜单中列表显示使用

	private Long companyId;//公司ID

	private Date createTime;//创建时间

	private String markPicture;//图标

	private String colour;//备用

	private String mark;//标记



	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public String getMenuName() {
		return menuName;
	}

	//set方法
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	//get方法
	public Long getParentId() {
		return parentId;
	}

	//set方法
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	//get方法
	public String getDisplayName() {
		return displayName;
	}

	//set方法
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	//get方法
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	//set方法
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	//get方法
	public String getUrl() {
		return url;
	}

	//set方法
	public void setUrl(String url) {
		this.url = url;
	}
	//get方法
	public Integer getState() {
		return state;
	}

	//set方法
	public void setState(Integer state) {
		this.state = state;
	}
	//get方法
	public String getImgUrl() {
		return imgUrl;
	}

	//set方法
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	//get方法
	public Long getStationId() {
		return stationId;
	}

	//set方法
	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	//get方法
	public Long getCompanyId() {
		return companyId;
	}

	//set方法
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	//get方法
	public Date getCreateTime() {
		return createTime;
	}

	//set方法
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	//get方法
	public String getMarkPicture() {
		return markPicture;
	}

	//set方法
	public void setMarkPicture(String markPicture) {
		this.markPicture = markPicture;
	}
	//get方法
	public String getColour() {
		return colour;
	}

	//set方法
	public void setColour(String colour) {
		this.colour = colour;
	}
	//get方法
	public String getMark() {
		return mark;
	}

	//set方法
	public void setMark(String mark) {
		this.mark = mark;
	}


}
