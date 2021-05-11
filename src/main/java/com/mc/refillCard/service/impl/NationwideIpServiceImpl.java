package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.NationwideIpMapper;
import com.mc.refillCard.dto.NationwideIpDto;
import com.mc.refillCard.entity.NationwideIp;
import com.mc.refillCard.entity.Transaction;
import com.mc.refillCard.service.NationwideIpService;
import com.mc.refillCard.service.TransactionService;
import com.mc.refillCard.vo.NationwideIpVo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/****
 * @Author: MC
 * @Description: NationwideIp业务层接口实现类
 * @Date 2021-4-3 16:33:59
 *****/
@Service
public class NationwideIpServiceImpl implements NationwideIpService {

    @Autowired
    private NationwideIpMapper nationwideIpMapper;
    @Lazy
    @Autowired
    private TransactionService transactionService;

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

    @Override
    public List<NationwideIp> findListByArea(String area) {
        return nationwideIpMapper.findListByArea(area);
    }

    @Override
    public Integer importDataIp(MultipartFile file) throws IOException {
        List<NationwideIp> nationwideIpList = new ArrayList<>();
        //获得一个工作薄
        XSSFWorkbook hssf = new XSSFWorkbook(file.getInputStream());
        //获得工作薄中第一个表单
        XSSFSheet hssfSheet = hssf.getSheetAt(0);
        int rows = hssfSheet.getPhysicalNumberOfRows();
        //读取行
        for (int i = 1; i <= rows; i++) {
            XSSFRow hssfRow = hssfSheet.getRow(i);
            if (hssfRow == null) {
                continue;
            }
            //地区
            String area = getCellValue(hssfRow.getCell(0));
            if(area.indexOf(" ")>-1){
                area = area.replace(" ","");
            }
            //开始IP
            String startIp = getCellValue(hssfRow.getCell(1));
            if(startIp.indexOf(" ")>-1){
                startIp = startIp.replace(" ","");
            }
            //结束IP
            String endIp = getCellValue(hssfRow.getCell(2));
            if(endIp.indexOf(" ")>-1){
                endIp = endIp.replace(" ","");
            }
            //问题地区类型 1没问题 2有问题
            String status = getCellValue(hssfRow.getCell(3));
            if(status.indexOf(" ")>-1){
                status = status.replace(" ","");
            }
            if(StringUtils.isNotBlank(area)){
                NationwideIp nationwideIp = new NationwideIp();
                nationwideIp.setArea(area);
                nationwideIp.setStartIp(startIp);
                nationwideIp.setEndIp(endIp);
                nationwideIp.setStatus(Integer.valueOf(status));
                nationwideIpList.add(nationwideIp);
            }
        }
        if(CollUtil.isNotEmpty(nationwideIpList)){
            nationwideIpMapper.batchAdd(nationwideIpList);
        }
        return nationwideIpList.size();
    }

    @Override
    public LinkedHashMap statistics() {
        LinkedHashMap  linkedHashMap = new LinkedHashMap();
        Map<String, Integer>  map = new HashMap();
        List<NationwideIp> all = nationwideIpMapper.findList();
        for (NationwideIp nationwideIp : all) {
            String area = nationwideIp.getArea();
            Transaction transaction = new Transaction();
            transaction.setBuyerArea(area);
            List<Transaction> listByParam = transactionService.findListByParam(transaction);
//            List<Transaction> listByParam = new ArrayList<>();
            map.put(area,listByParam.size());
        }
        // 由于HashMap不属于list子类，所以无法使用Collections.sort方法来进行排序，所以我们将hashmap中的entryset取出放入一个ArrayList中
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

        // 根据entryset中value的值，对ArrayList中的entryset进行排序，最终达到我们对hashmap的值进行排序的效果
        Collections.sort(list, (o1, o2) -> {
            // 升序排序
            return o1.getValue().compareTo(o2.getValue());
        });

        //用迭代器对list中的元素遍历
        Iterator<Map.Entry<String, Integer>> iterator = list.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            String key = entry.getKey();
            int value = entry.getValue();
            linkedHashMap.put(key,value);
            System.out.println(key + "：" + value);
        }
        return linkedHashMap;
    }

    public String getCellValue(XSSFCell cell) {
        if(cell == null){
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case Cell.CELL_TYPE_NUMERIC:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            default:
                return "";
        }
    }


}
