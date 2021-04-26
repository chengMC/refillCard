package com.mc.refillCard.service.impl;
import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.UserRelateMapper;
import com.mc.refillCard.dto.UserBalanceDto;
import com.mc.refillCard.dto.UserRelateDto;
import com.mc.refillCard.entity.User;
import com.mc.refillCard.entity.UserRelate;
import com.mc.refillCard.service.UserRelateService;
import com.mc.refillCard.service.UserService;
import com.mc.refillCard.vo.UserRelateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
/****
 * @Author: MC
 * @Description: UserRelate业务层接口实现类
 * @Date 2021-3-21 16:20:43
 *****/
@Service
public class UserRelateServiceImpl implements UserRelateService {

    @Autowired
    private UserRelateMapper userRelateMapper;
    @Autowired
    private UserService userService;

    /**
     UserRelate条件+分页查询
     @return 分页结果
      * @param userRelateDto 查询条件
     * @param page      页码
     * @param size      页大小
     */
    @Override
    public PageInfo findPage(UserRelateDto userRelateDto, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        List<UserRelateVo> userRelateVos = userRelateMapper.findPageVoByExample(userRelateDto);
        //执行搜索
        return new PageInfo(userRelateVos);
    }

    /**
     * UserRelate条件查询
     * @param userRelate
     * @return
     */
    @Override
    public List<UserRelate> findList(UserRelate userRelate){
        //根据构建的条件查询数据
        return userRelateMapper.selectByExample(userRelate);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        userRelateMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改UserRelate
     * @param userRelate
     */
    @Override
    public void update(UserRelate userRelate){
        userRelateMapper.updateByPrimaryKeySelective(userRelate);
    }

    /**
     * 修改UserRelate
     * @param userRelateDto
     */
    @Override
    public void updateDto(UserRelateDto userRelateDto){
        UserRelate userRelate = BeanUtil.copyProperties(userRelateDto, UserRelate.class);
        userRelateMapper.updateByPrimaryKeySelective(userRelate);
    }

    /**
     * 增加UserRelate
     * @param userRelate
     */
    @Override
    public void add(UserRelate userRelate){
        userRelateMapper.insertSelective(userRelate);
    }

    /**
     * 增加UserRelate
     * @param userRelateDto
     */
    @Override
    public void addDto(UserRelateDto userRelateDto){
        UserRelate userRelate = BeanUtil.copyProperties(userRelateDto, UserRelate.class);
        userRelateMapper.insertSelective(userRelate);
    }

    /**
     * 根据ID查询UserRelate
     * @param id
     * @return
     */
    @Override
    public UserRelate findById(Long id){
        return  userRelateMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询UserRelate全部数据
     * @return
     */
    @Override
    public List<UserRelate> findAll() {
        return userRelateMapper.findAll();
    }

    @Override
    public UserRelate findByPlatformUserId(String platformUserId) {
        return userRelateMapper.findByPlatformUserId(platformUserId);
    }

    @Override
    public void updateBalance(UserBalanceDto userBalanceDto) {
        BigDecimal balance = userBalanceDto.getBalance();
        UserRelate userRelate = userRelateMapper.selectByPrimaryKey(userBalanceDto.getId());
        //根据用户id查询用户余额
        Long userId = userRelate.getUserId();
        User user = userService.findById(userId);
        BigDecimal userBalance = user.getBalance();
        //加款
        BigDecimal ultimatelyBalance = userBalance.add(balance);
        user.setBalance(ultimatelyBalance);
        userService.update(user);
    }

}
