package com.mc.refillCard.dao;

import com.mc.refillCard.entity.NationwideIp;
import com.mc.refillCard.dto.NationwideIpDto;
import com.mc.refillCard.vo.NationwideIpVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/****
 * @Author: MC
 * @Description:NationwideIp的Dao
 * @Date 2021-4-3 16:33:59
 *****/
@Mapper
public interface NationwideIpMapper {

    int deleteByPrimaryKey(Long id);

    int insert(NationwideIp nationwideIp);

    int insertSelective(NationwideIp nationwideIp);

    NationwideIp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NationwideIp nationwideIp);

    int updateByPrimaryKey(NationwideIp nationwideIp);

    List<NationwideIp> selectByExample(NationwideIp nationwideIp);

    List<NationwideIp> findAll();

    List<NationwideIpVo> findPageVoByExample(NationwideIpDto nationwideIpDto);

    List<NationwideIp> findListByArea(@Param("area") String area);

    void batchAdd(@Param("nationwideIpList") List<NationwideIp> nationwideIpList);

    List<NationwideIp> findList();

}
