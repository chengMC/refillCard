package com.mc.refillCard.service;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dto.GameServerDto;
import com.mc.refillCard.entity.GameServer;
import com.mc.refillCard.vo.GameServerVo;

import java.util.List;

/****
 * @Author: MC
 * @Description:GameServer业务层接口
 * @Date 2021-5-10 23:11:50
 *****/
public interface GameServerService {


    /***
     * GameServer多条件分页查询
     * @param gameServerDto
     * @param page
     * @param size
     * @return
     */
    PageInfo<GameServerVo> findPage(GameServerDto gameServerDto, int page, int size);

    /***
     * GameServer多条件搜索方法
     * @param gameServer
     * @return
     */
    List<GameServer> findList(GameServer gameServer);

    /**
    * 根据ID查询GameServer
    * @param id
    * @return
    */
    GameServer findById(Long id);

    /***
     * 删除GameServer
     * @param id
     */
    void delete(Long id);

    /***
     * 修改GameServer数据
     * @param gameServer
     */
    void update(GameServer gameServer);

    /***
     * 修改GameServer数据
     * @param gameServerDto
     */
    void updateDto(GameServerDto gameServerDto);

    /***
     * 新增GameServer
     * @param gameServer
     */
    void add(GameServer gameServer);
    /***
     * 新增GameServer
     * @param gameServerDto
     */
    void addDto(GameServerDto gameServerDto);

    /***
     * 查询所有GameServer
     * @return
     */
    List<GameServer> findAll();

    /**
     * 根据游戏商品查询游戏区
     *
     * @param goodType
     * @return
     */
    List<GameServer> findListByGoodType(Long goodType);
}
