package com.example.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.MedicalExamSign;
import com.example.exception.CustomException;
import com.example.mapper.MedicalExamSignMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 体检报名业务处理
 **/
@Service
public class MedicalExamSignService {

    @Resource
    private MedicalExamSignMapper medicalExamSignMapper;

    /**
     * 新增
     */
    public void add(MedicalExamSign medicalExamSign) {
        medicalExamSign.setDate(DateUtil.today());
        MedicalExamSign sign = medicalExamSignMapper.selectByExamItemIdAndUserId(medicalExamSign.getExamItemId(), medicalExamSign.getUserId());
        if (sign != null) {
            throw new CustomException(ResultCodeEnum.SIGN_ERROR);
        }

        medicalExamSignMapper.insert(medicalExamSign);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        medicalExamSignMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            medicalExamSignMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(MedicalExamSign medicalExamSign) {
        medicalExamSignMapper.updateById(medicalExamSign);
    }

    /**
     * 根据ID查询
     */
    public MedicalExamSign selectById(Integer id) {
        return medicalExamSignMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<MedicalExamSign> selectAll(MedicalExamSign medicalExamSign) {
        return medicalExamSignMapper.selectAll(medicalExamSign);
    }

    /**
     * 分页查询
     */
    public PageInfo<MedicalExamSign> selectPage(MedicalExamSign medicalExamSign, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals(RoleEnum.USER.name())) {
            medicalExamSign.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<MedicalExamSign> list = medicalExamSignMapper.selectAll(medicalExamSign);
        return PageInfo.of(list);
    }

    public MedicalExamSign selectByExamItemIdAndUserId(Integer examItemId, Integer userId) {
        return medicalExamSignMapper.selectByExamItemIdAndUserId(examItemId, userId);
    }

}