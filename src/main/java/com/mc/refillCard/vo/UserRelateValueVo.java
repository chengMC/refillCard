package com.mc.refillCard.vo;

import lombok.Data;

import java.io.Serializable;

/****
 * @Author: MC
 * @Description:UserRelate构建
 * @Date 2021-3-26 16:48:32
 *****/
@Data
public class UserRelateValueVo implements Serializable{


	/**
	 * 用户id
	 */
	private Long userId;

	private String userName;

	private String nickName;

	private String phone;


}
