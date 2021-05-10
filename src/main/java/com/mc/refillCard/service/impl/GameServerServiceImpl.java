package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.GameServerMapper;
import com.mc.refillCard.dto.GameServerDto;
import com.mc.refillCard.entity.GameServer;
import com.mc.refillCard.service.GameServerService;
import com.mc.refillCard.vo.GameServerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/****
 * @Author: MC
 * @Description: GameServer业务层接口实现类
 * @Date 2021-5-10 23:11:50
 *****/
@Service
public class GameServerServiceImpl implements GameServerService {

    @Autowired
    private GameServerMapper gameServerMapper;

    /**
     GameServer条件+分页查询
     @param gameServerDto 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(GameServerDto gameServerDto, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        List<GameServerVo> gameServerVos = gameServerMapper.findPageVoByExample(gameServerDto);
        //执行搜索
        return new PageInfo(gameServerVos);
    }

    /**
     * GameServer条件查询
     * @param gameServer
     * @return
     */
    @Override
    public List<GameServer> findList(GameServer gameServer){
        //根据构建的条件查询数据
        return gameServerMapper.selectByExample(gameServer);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        gameServerMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改GameServer
     * @param gameServer
     */
    @Override
    public void update(GameServer gameServer){
        gameServerMapper.updateByPrimaryKeySelective(gameServer);
    }

    /**
     * 修改GameServer
     * @param gameServerDto
     */
    @Override
    public void updateDto(GameServerDto gameServerDto){
        GameServer gameServer = BeanUtil.copyProperties(gameServerDto, GameServer.class);
        gameServerMapper.updateByPrimaryKeySelective(gameServer);
    }

    /**
     * 增加GameServer
     * @param gameServer
     */
    @Override
    public void add(GameServer gameServer){
        gameServerMapper.insertSelective(gameServer);
    }

    /**
     * 增加GameServer
     * @param gameServerDto
     */
    @Override
    public void addDto(GameServerDto gameServerDto){
        GameServer gameServer = BeanUtil.copyProperties(gameServerDto, GameServer.class);
        gameServerMapper.insertSelective(gameServer);
    }

    /**
     * 根据ID查询GameServer
     * @param id
     * @return
     */
    @Override
    public GameServer findById(Long id){
        return  gameServerMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询GameServer全部数据
     * @return
     */
    @Override
    public List<GameServer> findAll() {
        return gameServerMapper.findAll();
    }

    @Override
    public List<GameServer> findListByGoodType(Long goodType) {
        return gameServerMapper.findListByGoodType(goodType);
    }


}
