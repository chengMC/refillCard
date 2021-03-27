package com.mc.refillCard.dto;

import lombok.Data;

/**
 * @author hu
 * @date 2020/11/17 下午2:56
 */
@Data
public class MenuDto {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单url
     */
    private String url;
    /**
     * 父菜单id
     */
    private Integer parentId;
    /**
     * 排序
     */
    private Integer displayOrder;
    /**
     * 菜单图标
     */
    private String img;
}
