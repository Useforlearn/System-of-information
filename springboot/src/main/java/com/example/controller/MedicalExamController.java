package com.example.controller;

import com.example.common.Result;
import com.example.entity.MedicalExam; // 假设体检实体类为MedicalExam，你可根据实际情况修改
import com.example.service.MedicalExamService; // 假设体检服务类为MedicalExamService，你可根据实际情况修改
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 体检信息表前端操作接口
 **/
@RestController
@RequestMapping("/medicalExam")
public class MedicalExamController {

    @Resource
    private MedicalExamService medicalExamService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody MedicalExam medicalExam) {
        medicalExamService.add(medicalExam);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        medicalExamService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        medicalExamService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody MedicalExam medicalExam) {
        medicalExamService.updateById(medicalExam);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        MedicalExam medicalExam = medicalExamService.selectById(id);
        return Result.success(medicalExam);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(MedicalExam medicalExam) {
        List<MedicalExam> list = medicalExamService.selectAll(medicalExam);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(MedicalExam medicalExam,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageNum2) {
        PageInfo<MedicalExam> page = medicalExamService.selectPage(medicalExam, pageNum, pageNum2);
        return Result.success(page);
    }
}