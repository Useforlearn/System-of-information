package com.example.mapper;

import com.example.entity.MedicalExam;
import java.util.List;

/**
 * 操作medicalExam相关数据接口
 */
public interface MedicalExamMapper {

    /**
     * 新增
     */
    int insert(MedicalExam medicalExam);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(MedicalExam medicalExam);

    /**
     * 根据ID查询
     */
    MedicalExam selectById(Integer id);

    /**
     * 查询所有
     */
    List<MedicalExam> selectAll(MedicalExam medicalExam);

}