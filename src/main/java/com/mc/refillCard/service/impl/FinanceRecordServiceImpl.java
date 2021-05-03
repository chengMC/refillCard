package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.FinanceRecordMapper;
import com.mc.refillCard.dto.FinanceRecordDto;
import com.mc.refillCard.entity.FinanceRecord;
import com.mc.refillCard.service.FinanceRecordService;
import com.mc.refillCard.vo.FinanceRecordVo;
import com.mc.refillCard.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/****
 * @Author: MC
 * @Description: FinanceRecord业务层接口实现类
 * @Date 2021-5-3 13:54:54
 *****/
@Service
public class FinanceRecordServiceImpl implements FinanceRecordService {

    @Autowired
    private FinanceRecordMapper financeRecordMapper;

    /**
     FinanceRecord条件+分页查询
     @param financeRecordDto 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(FinanceRecordDto financeRecordDto, int page, int size) {
        UserVo user = (UserVo) SecurityUtils.getSubject().getPrincipal();
        //管理员
        if(!"4".equals(user.getRoleId())){
            financeRecordDto.setUserId(user.getId());
        }
        //分页
        PageHelper.startPage(page, size);
        List<FinanceRecordVo> financeRecordVos = financeRecordMapper.findPageVoByExample(financeRecordDto);
        //执行搜索
        return new PageInfo(financeRecordVos);
    }

    /**
     * FinanceRecord条件查询
     * @param financeRecord
     * @return
     */
    @Override
    public List<FinanceRecord> findList(FinanceRecord financeRecord){
        //根据构建的条件查询数据
        return financeRecordMapper.selectByExample(financeRecord);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        financeRecordMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改FinanceRecord
     * @param financeRecord
     */
    @Override
    public void update(FinanceRecord financeRecord){
        financeRecordMapper.updateByPrimaryKeySelective(financeRecord);
    }

    /**
     * 修改FinanceRecord
     * @param financeRecordDto
     */
    @Override
    public void updateDto(FinanceRecordDto financeRecordDto){
        FinanceRecord financeRecord = BeanUtil.copyProperties(financeRecordDto, FinanceRecord.class);
        financeRecordMapper.updateByPrimaryKeySelective(financeRecord);
    }

    /**
     * 增加FinanceRecord
     * @param financeRecord
     */
    @Override
    public void add(FinanceRecord financeRecord){
        financeRecordMapper.insertSelective(financeRecord);
    }

    /**
     * 增加FinanceRecord
     * @param financeRecordDto
     */
    @Override
    public void addDto(FinanceRecordDto financeRecordDto){
        FinanceRecord financeRecord = BeanUtil.copyProperties(financeRecordDto, FinanceRecord.class);
        financeRecordMapper.insertSelective(financeRecord);
    }

    /**
     * 根据ID查询FinanceRecord
     * @param id
     * @return
     */
    @Override
    public FinanceRecord findById(Long id){
        return  financeRecordMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询FinanceRecord全部数据
     * @return
     */
    @Override
    public List<FinanceRecord> findAll() {
        return financeRecordMapper.findAll();
    }


}
