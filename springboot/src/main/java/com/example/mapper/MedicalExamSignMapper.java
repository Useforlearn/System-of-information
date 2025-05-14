package com.example.mapper;

import com.example.entity.MedicalExamSign;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作medical_exam_sign相关数据接口
 */
public interface MedicalExamSignMapper {

    /**
     * 新增
     */
    int insert(MedicalExamSign medicalExamSign);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(MedicalExamSign medicalExamSign);

    /**
     * 根据ID查询
     */
    MedicalExamSign selectById(Integer id);

    /**
     * 查询所有
     */
    List<MedicalExamSign> selectAll(MedicalExamSign medicalExamSign);

    @Select("select * from medical_exam_sign where exam_item_id =#{examItemId} and user_id =#{userId} and status != '审核拒绝'")
    MedicalExamSign selectByExamItemIdAndUserId(@Param("examItemId") Integer examItemId, @Param("userId") Integer userId);
}