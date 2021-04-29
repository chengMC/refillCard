package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.UserPricingMapper;
import com.mc.refillCard.dto.UserPricingDto;
import com.mc.refillCard.entity.UserPricing;
import com.mc.refillCard.service.UserPricingService;
import com.mc.refillCard.vo.UserPricingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/****
 * @Author: MC
 * @Description: UserPricing业务层接口实现类
 * @Date 2021-4-24 21:40:05
 *****/
@Service
public class UserPricingServiceImpl implements UserPricingService {

    @Autowired
    private UserPricingMapper userPricingMapper;

    /**
     UserPricing条件+分页查询
     @param userPricingDto 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(UserPricingDto userPricingDto, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        List<UserPricingVo> userPricingVos = userPricingMapper.findPageVoByExample(userPricingDto);
        //执行搜索
        return new PageInfo(userPricingVos);
    }

    /**
     * UserPricing条件查询
     * @param userPricing
     * @return
     */
    @Override
    public List<UserPricing> findList(UserPricing userPricing){
        //根据构建的条件查询数据
        return userPricingMapper.selectByExample(userPricing);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        userPricingMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改UserPricing
     * @param userPricing
     */
    @Override
    public void update(UserPricing userPricing){
        userPricingMapper.updateByPrimaryKeySelective(userPricing);
    }

    /**
     * 修改UserPricing
     * @param userPricingDto
     */
    @Override
    public void updateDto(UserPricingDto userPricingDto){
        UserPricing userPricing = BeanUtil.copyProperties(userPricingDto, UserPricing.class);
        userPricingMapper.updateByPrimaryKeySelective(userPricing);
    }

    /**
     * 增加UserPricing
     * @param userPricing
     */
    @Override
    public void add(UserPricing userPricing){
        userPricingMapper.insertSelective(userPricing);
    }

    /**
     * 增加UserPricing
     * @param userPricingDto
     */
    @Override
    public void addDto(UserPricingDto userPricingDto){
        UserPricing userPricing = BeanUtil.copyProperties(userPricingDto, UserPricing.class);
        userPricingMapper.insertSelective(userPricing);
    }

    /**
     * 根据ID查询UserPricing
     * @param id
     * @return
     */
    @Override
    public UserPricing findById(Long id){
        return  userPricingMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询UserPricing全部数据
     * @return
     */
    @Override
    public List<UserPricing> findAll() {
        return userPricingMapper.findAll();
    }

    @Override
    public UserPricing findByUserIdAndType(Long userId, Integer type) {
        return userPricingMapper.findByUserIdAndType(userId,type);
    }

    @Override
    public void updatePricing(UserPricingDto userPricingDto) {
        UserPricing userPricing = new UserPricing();
        userPricing.setId(userPricingDto.getId());
        userPricing.setUnifyPrice(userPricingDto.getUnifyPrice());
        userPricingMapper.updateByPrimaryKeySelective(userPricing);
    }


}
