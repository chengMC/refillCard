package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Enum.FinanceRecordTypeEnum;
import com.mc.refillCard.dao.UserRelateMapper;
import com.mc.refillCard.dto.UserBalanceDto;
import com.mc.refillCard.dto.UserRelateDto;
import com.mc.refillCard.entity.FinanceRecord;
import com.mc.refillCard.entity.User;
import com.mc.refillCard.entity.UserRelate;
import com.mc.refillCard.service.FinanceRecordService;
import com.mc.refillCard.service.UserRelateService;
import com.mc.refillCard.service.UserService;
import com.mc.refillCard.util.AccountUtils;
import com.mc.refillCard.vo.UserRelateVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    @Lazy
    @Autowired
    private UserService userService;
    @Autowired
    private FinanceRecordService financeRecordService;

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
    public void updateBalance(UserBalanceDto userBalanceDto) throws Exception {
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

        //添加加款记录
        FinanceRecord financeRecord = new FinanceRecord();
        financeRecord.setUserId(userId);
        financeRecord.setTransactionBefore(userBalance);
        financeRecord.setTransactionAfter(ultimatelyBalance);
        financeRecord.setTransactionMoney(balance);
        financeRecord.setTransactionType(FinanceRecordTypeEnum.ADD.getCode());
        financeRecord.setCreateTime(DateUtil.date());
        financeRecord.setRemark(userBalanceDto.getRemark());
        financeRecordService.add(financeRecord);
    }

    @Override
    public void updateBalanceV1(UserBalanceDto userBalanceDto) throws Exception {
        String passWord = userBalanceDto.getPassWord();
        if(StringUtils.isEmpty(passWord)){
            throw new Exception("请输入加款密码");
        }
        BigDecimal balance = userBalanceDto.getBalance();
        UserRelate userRelate = userRelateMapper.selectByPrimaryKey(userBalanceDto.getId());
        //根据用户id查询用户余额
        Long userId = userRelate.getUserId();
        User user = userService.findById(userId);
        BigDecimal userBalance = user.getBalance();
        String departmentName = user.getDepartmentName();
        //用户名加盐
        String accountSecret = AccountUtils.createAccountSecret(user.getUserName(), passWord);
        if(!accountSecret.equals(departmentName)){
            throw new Exception("加款密码输入错误，请重新输入");
        }
        //加款
        BigDecimal ultimatelyBalance = userBalance.add(balance);
        user.setBalance(ultimatelyBalance);
        //修改加款
        userService.update(user);
        //添加加款记录
        FinanceRecord financeRecord = new FinanceRecord();
        financeRecord.setUserId(userId);
        financeRecord.setTransactionBefore(userBalance);
        financeRecord.setTransactionAfter(ultimatelyBalance);
        financeRecord.setTransactionMoney(balance);
        financeRecord.setTransactionType(FinanceRecordTypeEnum.ADD.getCode());
        financeRecord.setCreateTime(DateUtil.date());
        financeRecord.setRemark(userBalanceDto.getRemark());
        financeRecordService.add(financeRecord);
    }

    @Override
    public void deductBalance(UserBalanceDto userBalanceDto) throws Exception {
        String passWord = userBalanceDto.getPassWord();
        if(StringUtils.isEmpty(passWord)){
            throw new Exception("请输入扣款密码");
        }
        BigDecimal balance = userBalanceDto.getBalance();
        UserRelate userRelate = userRelateMapper.selectByPrimaryKey(userBalanceDto.getId());
        //根据用户id查询用户余额
        Long userId = userRelate.getUserId();
        User user = userService.findById(userId);
        String departmentName = user.getDepartmentName();
        //用户名加盐
        String accountSecret = AccountUtils.createAccountSecret(user.getUserName(), passWord);
        if(!accountSecret.equals(departmentName)){
            throw new Exception("扣款密码输入错误，请重新输入");
        }
        BigDecimal userBalance = user.getBalance();
        //账号余额小于扣款金额
        if(userBalance.compareTo(balance) == -1){
            throw new Exception("账号余额少于输入的扣款金额，请重新输入");
        }

        //扣款
        BigDecimal ultimatelyBalance = userBalance.subtract(balance);
        user.setBalance(ultimatelyBalance);
        userService.update(user);

        //添加扣款记录
        FinanceRecord financeRecord = new FinanceRecord();
        financeRecord.setUserId(userId);
        financeRecord.setTransactionBefore(userBalance);
        financeRecord.setTransactionAfter(ultimatelyBalance);
        financeRecord.setTransactionMoney(balance);
        financeRecord.setTransactionType(FinanceRecordTypeEnum.SUBTRACT.getCode());
        financeRecord.setCreateTime(DateUtil.date());
        financeRecord.setRemark(userBalanceDto.getRemark());
        financeRecordService.add(financeRecord);
    }

}
