package com.mc.refillCard.dao;

import com.mc.refillCard.dto.GameServerDto;
import com.mc.refillCard.entity.GameServer;
import com.mc.refillCard.vo.GameServerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/****
 * @Author: MC
 * @Description:GameServer的Dao
 * @Date 2021-5-10 23:11:50
 *****/
@Mapper
public interface GameServerMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GameServer gameServer);

    int insertSelective(GameServer gameServer);

    GameServer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GameServer gameServer);

    int updateByPrimaryKey(GameServer gameServer);

    List<GameServer> selectByExample(GameServer gameServer);

    List<GameServer> findAll();

    List<GameServerVo> findPageVoByExample(GameServerDto gameServerDto);

    List<GameServer> findListByGoodType(@Param("goodType") Long goodType);
}
