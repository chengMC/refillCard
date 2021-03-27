package com.mc.refillCard.vo;

import lombok.Data;

import java.io.Serializable;

/****
 *
 * 更新备注
 *
 * @Author: MC
 * @Description:Transaction构建
 * @Date 2021-3-20 21:17:23
 *****/
@Data
public class TaobaoDoMemoUpdateVo implements Serializable{

	/**
	 * Flag是旗帜，可以传0-5的数字，如果传-1或者不传此参数，则保留原旗帜；
	 */
	private int Flag;

	/**
	 * 备注内容
	 */
	private String Memo;


}
