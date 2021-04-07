package com.mc.refillCard.service.impl;
import com.mc.refillCard.dao.GoodTypeMapper;
import com.mc.refillCard.entity.GoodType;
import com.mc.refillCard.dto.GoodTypeDto;
import com.mc.refillCard.vo.GoodTypeVo;
import com.mc.refillCard.service.GoodTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import cn.hutool.core.bean.BeanUtil;
import java.util.List;
/****
 * @Author: MC
 * @Description: GoodType业务层接口实现类
 * @Date 2021-4-7 21:05:20
 *****/
@Service
public class GoodTypeServiceImpl implements GoodTypeService {

    @Autowired
    private GoodTypeMapper goodTypeMapper;

    /**
     GoodType条件+分页查询
     @param goodTypeDto 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(GoodTypeDto goodTypeDto, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        List<GoodTypeVo> goodTypeVos = goodTypeMapper.findPageVoByExample(goodTypeDto);
        //执行搜索
        return new PageInfo(goodTypeVos);
    }

    /**
     * GoodType条件查询
     * @param goodType
     * @return
     */
    @Override
    public List<GoodType> findList(GoodType goodType){
        //根据构建的条件查询数据
        return goodTypeMapper.selectByExample(goodType);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        goodTypeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改GoodType
     * @param goodType
     */
    @Override
    public void update(GoodType goodType){
        goodTypeMapper.updateByPrimaryKeySelective(goodType);
    }

    /**
     * 修改GoodType
     * @param goodTypeDto
     */
    @Override
    public void updateDto(GoodTypeDto goodTypeDto){
        GoodType goodType = BeanUtil.copyProperties(goodTypeDto, GoodType.class);
        goodTypeMapper.updateByPrimaryKeySelective(goodType);
    }

    /**
     * 增加GoodType
     * @param goodType
     */
    @Override
    public void add(GoodType goodType){
        goodTypeMapper.insertSelective(goodType);
    }

    /**
     * 增加GoodType
     * @param goodTypeDto
     */
    @Override
    public void addDto(GoodTypeDto goodTypeDto){
        GoodType goodType = BeanUtil.copyProperties(goodTypeDto, GoodType.class);
        goodTypeMapper.insertSelective(goodType);
    }

    /**
     * 根据ID查询GoodType
     * @param id
     * @return
     */
    @Override
    public GoodType findById(Long id){
        return  goodTypeMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询GoodType全部数据
     * @return
     */
    @Override
    public List<GoodType> findAll() {
        return goodTypeMapper.findAll();
    }


}
