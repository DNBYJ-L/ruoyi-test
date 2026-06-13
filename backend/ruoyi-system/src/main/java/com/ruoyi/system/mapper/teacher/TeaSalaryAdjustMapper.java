package com.ruoyi.system.mapper.teacher;

import java.util.List;
import com.ruoyi.system.domain.teacher.TeaSalaryAdjust;

/**
 * 薪资调整记录 数据层
 * 
 * @author ruoyi
 */
public interface TeaSalaryAdjustMapper
{
    public List<TeaSalaryAdjust> selectAdjustByTeacherId(Long teacherId);
    public int insertAdjust(TeaSalaryAdjust adjust);
}
