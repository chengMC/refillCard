package com.mc.refillCard.dao;

import com.mc.refillCard.dto.LogInformationDto;
import com.mc.refillCard.entity.LogInformation;
import com.mc.refillCard.vo.LogInformationVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/****
 * @Author: MC
 * @Description:Log的Dao
 * @Date 2020-5-29 9:55:47
 *****/
@Mapper
public interface LogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(LogInformation logInformation);

    int insertSelective(LogInformation logInformation);

    LogInformation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LogInformation logInformation);

    int updateByPrimaryKey(LogInformation logInformation);

    List<LogInformation> selectByExample( LogInformation logInformation);

    List<LogInformation> findAll(Long companyId);

    List<LogInformationVo> findPage(LogInformationDto logInformationDto);
}
