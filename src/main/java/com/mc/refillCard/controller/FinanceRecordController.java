package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.annotation.SystemControllerLog;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.FinanceRecordDto;
import com.mc.refillCard.entity.FinanceRecord;
import com.mc.refillCard.service.FinanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: MC
 * @Description:
 * @Date 2021-5-3 13:54:54
 *****/
@RestController
@RequestMapping("/financeRecord")
@CrossOrigin
public class FinanceRecordController {

    @Autowired
    private FinanceRecordService financeRecordService;

    /***
     * 多条件搜索financeRecord数据
     * @param financeRecordDto
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result findPage(FinanceRecordDto financeRecordDto,@PathVariable int page, @PathVariable int size) {
        PageInfo pageInfo = financeRecordService.findPage(financeRecordDto, page, size);
        return Result.success("查询成功", pageInfo);
    }

    /***
     * 查询FinanceRecord全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result findAll(){
        //调用FinanceRecordService实现查询所有FinanceRecord
        List<FinanceRecord> list = financeRecordService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索financeRecord数据
     * @param financeRecord
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result  findList(FinanceRecord financeRecord){
        //调用FinanceRecordService实现条件查询FinanceRecord
        List<FinanceRecord> list = financeRecordService.findList(financeRecord);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询FinanceRecord数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result  findById(@PathVariable Long id){
        //调用FinanceRecordService实现根据主键查询FinanceRecord
        FinanceRecord financeRecord = financeRecordService.findById(id);
        return Result.success("查询成功",financeRecord);
    }

    /***
     * 根据ID删除financeRecord数据
     * @param id
     * @return
     */
    @SystemControllerLog(description = "管理:删除")
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        //调用FinanceRecordService实现根据主键删除
        financeRecordService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改FinanceRecord数据
     * @param financeRecordDto
     * @param
     * @return
     */
    @SystemControllerLog(description = "管理:编辑")
    @PostMapping(value="/update")
    public Result update(@RequestBody FinanceRecordDto financeRecordDto){
        //调用FinanceRecordService实现修改FinanceRecord
       Long id = financeRecordDto.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        financeRecordService.updateDto(financeRecordDto);
        return Result.success("编辑成功");
    }

    /***
     * 新增FinanceRecord数据
     * @param financeRecordDto
     * @return
     */
    @SystemControllerLog(description = "管理:新增")
    @PostMapping(value="/add")
    public Result add(@RequestBody FinanceRecordDto financeRecordDto){
        //调用FinanceRecordService实现添加FinanceRecord
        financeRecordService.addDto(financeRecordDto);
        return Result.success("新增成功");
    }


}
