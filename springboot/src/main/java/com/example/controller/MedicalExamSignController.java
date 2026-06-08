package com.example.controller;

import cn.hutool.core.lang.Dict;
import com.example.common.Result;
import com.example.entity.MedicalExamSign;
import com.example.service.MedicalExamSignService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 体检报名前端操作接口
 **/
@RestController
@RequestMapping("/medicalExamSign")
public class MedicalExamSignController {

    @Resource
    private MedicalExamSignService medicalExamSignService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody MedicalExamSign medicalExamSign) {
        medicalExamSignService.add(medicalExamSign);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        medicalExamSignService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        medicalExamSignService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody MedicalExamSign medicalExamSign) {
        medicalExamSignService.updateById(medicalExamSign);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        MedicalExamSign medicalExamSign = medicalExamSignService.selectById(id);
        return Result.success(medicalExamSign);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(MedicalExamSign medicalExamSign) {
        List<MedicalExamSign> list = medicalExamSignService.selectAll(medicalExamSign);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(MedicalExamSign medicalExamSign,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageNum2) {
        PageInfo<MedicalExamSign> page = medicalExamSignService.selectPage(medicalExamSign, pageNum, pageNum2);
        return Result.success(page);
    }
}