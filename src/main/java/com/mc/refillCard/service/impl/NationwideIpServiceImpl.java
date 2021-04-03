package com.mc.refillCard.service.impl;
import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.NationwideIpMapper;
import com.mc.refillCard.dto.NationwideIpDto;
import com.mc.refillCard.entity.NationwideIp;
import com.mc.refillCard.service.NationwideIpService;
import com.mc.refillCard.vo.NationwideIpVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/****
 * @Author: MC
 * @Description: NationwideIp业务层接口实现类
 * @Date 2021-4-3 16:33:59
 *****/
@Service
public class NationwideIpServiceImpl implements NationwideIpService {

    @Autowired
    private NationwideIpMapper nationwideIpMapper;

    /**
     NationwideIp条件+分页查询
     @param nationwideIpDto 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(NationwideIpDto nationwideIpDto, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        List<NationwideIpVo> nationwideIpVos = nationwideIpMapper.findPageVoByExample(nationwideIpDto);
        //执行搜索
        return new PageInfo(nationwideIpVos);
    }

    /**
     * NationwideIp条件查询
     * @param nationwideIp
     * @return
     */
    @Override
    public List<NationwideIp> findList(NationwideIp nationwideIp){
        //根据构建的条件查询数据
        return nationwideIpMapper.selectByExample(nationwideIp);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        nationwideIpMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改NationwideIp
     * @param nationwideIp
     */
    @Override
    public void update(NationwideIp nationwideIp){
        nationwideIpMapper.updateByPrimaryKeySelective(nationwideIp);
    }

    /**
     * 修改NationwideIp
     * @param nationwideIpDto
     */
    @Override
    public void updateDto(NationwideIpDto nationwideIpDto){
        NationwideIp nationwideIp = BeanUtil.copyProperties(nationwideIpDto, NationwideIp.class);
        nationwideIpMapper.updateByPrimaryKeySelective(nationwideIp);
    }

    /**
     * 增加NationwideIp
     * @param nationwideIp
     */
    @Override
    public void add(NationwideIp nationwideIp){
        nationwideIpMapper.insertSelective(nationwideIp);
    }

    /**
     * 增加NationwideIp
     * @param nationwideIpDto
     */
    @Override
    public void addDto(NationwideIpDto nationwideIpDto){
        NationwideIp nationwideIp = BeanUtil.copyProperties(nationwideIpDto, NationwideIp.class);
        nationwideIpMapper.insertSelective(nationwideIp);
    }

    /**
     * 根据ID查询NationwideIp
     * @param id
     * @return
     */
    @Override
    public NationwideIp findById(Long id){
        return  nationwideIpMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询NationwideIp全部数据
     * @return
     */
    @Override
    public List<NationwideIp> findAll() {
        return nationwideIpMapper.findAll();
    }


}
