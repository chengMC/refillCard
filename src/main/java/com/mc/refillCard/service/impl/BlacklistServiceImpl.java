package com.mc.refillCard.service.impl;
import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Enum.GoodsRelateTypeEnum;
import com.mc.refillCard.dao.BlacklistMapper;
import com.mc.refillCard.dto.BlacklistDto;
import com.mc.refillCard.entity.Blacklist;
import com.mc.refillCard.service.BlacklistService;
import com.mc.refillCard.vo.BlacklistVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/****
 * @Author: MC
 * @Description: Blacklist业务层接口实现类
 * @Date 2021-4-7 21:03:32
 *****/
@Service
public class BlacklistServiceImpl implements BlacklistService {

    @Autowired
    private BlacklistMapper blacklistMapper;

    /**
     Blacklist条件+分页查询
     @param blacklistDto 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(BlacklistDto blacklistDto, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        List<BlacklistVo> blacklistVos = blacklistMapper.findPageVoByExample(blacklistDto);
        //执行搜索
        return new PageInfo(blacklistVos);
    }

    /**
     * Blacklist条件查询
     * @param blacklist
     * @return
     */
    @Override
    public List<Blacklist> findList(Blacklist blacklist){
        //根据构建的条件查询数据
        return blacklistMapper.selectByExample(blacklist);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        blacklistMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Blacklist
     * @param blacklist
     */
    @Override
    public void update(Blacklist blacklist){
        blacklistMapper.updateByPrimaryKeySelective(blacklist);
    }

    /**
     * 修改Blacklist
     * @param blacklistDto
     */
    @Override
    public void updateDto(BlacklistDto blacklistDto){
        Blacklist blacklist = BeanUtil.copyProperties(blacklistDto, Blacklist.class);
        blacklistMapper.updateByPrimaryKeySelective(blacklist);
    }

    /**
     * 增加Blacklist
     * @param blacklist
     */
    @Override
    public void add(Blacklist blacklist){
        blacklistMapper.insertSelective(blacklist);
    }

    /**
     * 增加Blacklist
     * @param blacklistDto
     */
    @Override
    public void addDto(BlacklistDto blacklistDto){
        Blacklist blacklist = BeanUtil.copyProperties(blacklistDto, Blacklist.class);
        blacklistMapper.insertSelective(blacklist);
    }

    /**
     * 根据ID查询Blacklist
     * @param id
     * @return
     */
    @Override
    public Blacklist findById(Long id){
        return  blacklistMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Blacklist全部数据
     * @return
     */
    @Override
    public List<Blacklist> findAll() {
        return blacklistMapper.findAll();
    }

    @Override
    public void batchAdd(String black) {
        String[] split = black.split(",");
        for (String s : split) {
            Blacklist blacklist = new Blacklist();
            blacklist.setAccount(s);
            blacklist.setGoodTypeId(Long.valueOf(GoodsRelateTypeEnum.QB.getCode()));
            blacklist.setGoodTypeName(GoodsRelateTypeEnum.QB.getName());
            blacklistMapper.insertSelective(blacklist);
        }
    }

    @Override
    public List<Blacklist> findListByAccount(Blacklist blacklist) {
        return blacklistMapper.findListByAccount(blacklist);
    }


}
