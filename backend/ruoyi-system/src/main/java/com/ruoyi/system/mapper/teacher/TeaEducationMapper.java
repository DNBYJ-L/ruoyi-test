package com.ruoyi.system.mapper.teacher;

import java.util.List;
import com.ruoyi.system.domain.teacher.TeaEducation;

/**
 * 教师学历 数据层
 * 
 * @author ruoyi
 */
public interface TeaEducationMapper
{
    /**
     * 通过教师ID查询学历列表
     * 
     * @param teacherId 教师ID
     * @return 学历集合
     */
    public List<TeaEducation> selectEducationByTeacherId(Long teacherId);

    /**
     * 新增学历记录
     * 
     * @param education 学历信息
     * @return 结果
     */
    public int insertEducation(TeaEducation education);

    /**
     * 修改学历记录
     * 
     * @param education 学历信息
     * @return 结果
     */
    public int updateEducation(TeaEducation education);

    /**
     * 通过教师ID删除学历记录
     * 
     * @param teacherId 教师ID
     * @return 结果
     */
    public int deleteEducationByTeacherId(Long teacherId);

    /**
     * 批量删除学历记录
     * 
     * @param teacherIds 教师ID数组
     * @return 结果
     */
    public int deleteEducationByTeacherIds(Long[] teacherIds);
}
