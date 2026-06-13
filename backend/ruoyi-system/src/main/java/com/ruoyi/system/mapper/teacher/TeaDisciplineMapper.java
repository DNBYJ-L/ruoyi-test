package com.ruoyi.system.mapper.teacher;

import java.util.List;
import com.ruoyi.system.domain.teacher.TeaDiscipline;

/**
 * 教师学科专业 数据层
 * 
 * @author ruoyi
 */
public interface TeaDisciplineMapper
{
    /**
     * 通过教师ID查询学科专业列表
     * 
     * @param teacherId 教师ID
     * @return 学科专业集合
     */
    public List<TeaDiscipline> selectDisciplineByTeacherId(Long teacherId);

    /**
     * 新增学科专业记录
     * 
     * @param discipline 学科专业信息
     * @return 结果
     */
    public int insertDiscipline(TeaDiscipline discipline);

    /**
     * 修改学科专业记录
     * 
     * @param discipline 学科专业信息
     * @return 结果
     */
    public int updateDiscipline(TeaDiscipline discipline);

    /**
     * 通过教师ID删除学科专业记录
     * 
     * @param teacherId 教师ID
     * @return 结果
     */
    public int deleteDisciplineByTeacherId(Long teacherId);

    /**
     * 批量删除学科专业记录
     * 
     * @param teacherIds 教师ID数组
     * @return 结果
     */
    public int deleteDisciplineByTeacherIds(Long[] teacherIds);
}
