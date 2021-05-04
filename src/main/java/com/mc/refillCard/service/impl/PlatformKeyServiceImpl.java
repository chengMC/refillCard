package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.PlatformKeyMapper;
import com.mc.refillCard.dto.PlatformKeyDto;
import com.mc.refillCard.entity.PlatformKey;
import com.mc.refillCard.service.PlatformKeyService;
import com.mc.refillCard.vo.PlatformKeyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/****
 * @Author: MC
 * @Description: PlatformKey业务层接口实现类
 * @Date 2021-5-4 22:30:48
 *****/
@Service
public class PlatformKeyServiceImpl implements PlatformKeyService {

    @Autowired
    private PlatformKeyMapper platformKeyMapper;

    /**
     PlatformKey条件+分页查询
     @param platformKeyDto 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(PlatformKeyDto platformKeyDto, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        List<PlatformKeyVo> platformKeyVos = platformKeyMapper.findPageVoByExample(platformKeyDto);
        //执行搜索
        return new PageInfo(platformKeyVos);
    }

    /**
     * PlatformKey条件查询
     * @param platformKey
     * @return
     */
    @Override
    public List<PlatformKey> findList(PlatformKey platformKey){
        //根据构建的条件查询数据
        return platformKeyMapper.selectByExample(platformKey);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        platformKeyMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改PlatformKey
     * @param platformKey
     */
    @Override
    public void update(PlatformKey platformKey){
        platformKeyMapper.updateByPrimaryKeySelective(platformKey);
    }

    /**
     * 修改PlatformKey
     * @param platformKeyDto
     */
    @Override
    public void updateDto(PlatformKeyDto platformKeyDto){
        PlatformKey platformKey = BeanUtil.copyProperties(platformKeyDto, PlatformKey.class);
        platformKeyMapper.updateByPrimaryKeySelective(platformKey);
    }

    /**
     * 增加PlatformKey
     * @param platformKey
     */
    @Override
    public void add(PlatformKey platformKey){
        platformKeyMapper.insertSelective(platformKey);
    }

    /**
     * 增加PlatformKey
     * @param platformKeyDto
     */
    @Override
    public void addDto(PlatformKeyDto platformKeyDto){
        PlatformKey platformKey = BeanUtil.copyProperties(platformKeyDto, PlatformKey.class);
        platformKeyMapper.insertSelective(platformKey);
    }

    /**
     * 根据ID查询PlatformKey
     * @param id
     * @return
     */
    @Override
    public PlatformKey findById(Long id){
        return  platformKeyMapper.selectByPrimaryKey(id);
    }

    @Override
    public PlatformKey findByAppType(Integer type) {
        return platformKeyMapper.findByAppType(type);
    }

    /**
     * 查询PlatformKey全部数据
     * @return
     */
    @Override
    public List<PlatformKey> findAll() {
        return platformKeyMapper.findAll();
    }

    @Override
    public PlatformKey updatePlatformKey(String id) {
        Long idLong = Long.valueOf(id);
        PlatformKey platformKey = platformKeyMapper.selectByPrimaryKey(idLong);
        List<PlatformKey> listByAppType = platformKeyMapper.findListByAppType(platformKey.getAppType());
        for (PlatformKey key : listByAppType) {
                if(!idLong.equals(key.getId())){
                    key.setStatus(0);
                }else{
                    key.setStatus(1);
                }
            platformKeyMapper.updateByPrimaryKeySelective(key);
        }
         platformKey = platformKeyMapper.selectByPrimaryKey(idLong);
        return platformKey;
    }


}
