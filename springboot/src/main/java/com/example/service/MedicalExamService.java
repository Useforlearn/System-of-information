package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.MedicalExamStatusEnum; // 修改为体检状态枚举
import com.example.entity.Account;
import com.example.entity.MedicalExam; // 修改为体检实体类
import com.example.entity.MedicalExamSign; // 修改为体检报名实体类
import com.example.mapper.MedicalExamMapper; // 修改为体检Mapper
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 体检信息表业务处理
 **/
@Service
public class MedicalExamService {

    @Resource
    private MedicalExamMapper medicalExamMapper; // 修改为体检Mapper

    @Resource
    private MedicalExamSignService medicalExamSignService; // 修改为体检报名Service

    /**
     * 新增
     */
    public void add(MedicalExam medicalExam) {
        medicalExamMapper.insert(medicalExam);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        medicalExamMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            medicalExamMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(MedicalExam medicalExam) {
        medicalExamMapper.updateById(medicalExam);
    }

    /**
     * 根据ID查询
     */
    public MedicalExam selectById(Integer id) {
        MedicalExam medicalExam = medicalExamMapper.selectById(id);
        Account currentUser = TokenUtils.getCurrentUser();
        MedicalExamSign medicalExamSign = medicalExamSignService.selectByExamItemIdAndUserId(id, currentUser.getId());

        // 根据是否有报名记录设置状态
        if (medicalExamSign == null) {
            medicalExam.setStatus(MedicalExamStatusEnum.NOT_SCHEDULED.getValue());
        } else {
            Date date = new Date();
            String start = medicalExam.getStart();
            String end = medicalExam.getEnd();

            // 根据体检开始和结束时间设置状态
            if (DateUtil.parseDate(start).isAfter(date)) {
                medicalExam.setStatus(MedicalExamStatusEnum.SCHEDULED.getValue());
            } else if (DateUtil.parseDate(end).isBefore(date)) {
                medicalExam.setStatus(MedicalExamStatusEnum.DONE.getValue());
            } else {
                medicalExam.setStatus(MedicalExamStatusEnum.NOT_DONE.getValue());
            }
        }

        return medicalExam;
    }

    /**
     * 查询所有
     */
    public List<MedicalExam> selectAll(MedicalExam medicalExam) {
        return medicalExamMapper.selectAll(medicalExam);
    }

    /**
     * 分页查询
     */
    public PageInfo<MedicalExam> selectPage(MedicalExam medicalExam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MedicalExam> list = medicalExamMapper.selectAll(medicalExam);
        return PageInfo.of(list);
    }
}