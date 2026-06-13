package com.ruoyi.system.service.teacher;

import java.util.List;
import com.ruoyi.system.domain.teacher.TeaTeacher;

/**
 * 教师信息 服务层
 * 
 * @author ruoyi
 */
public interface ITeaTeacherService
{
    /**
     * 查询教师列表
     * 
     * @param teacher 教师信息
     * @return 教师集合
     */
    public List<TeaTeacher> selectTeacherList(TeaTeacher teacher);

    /**
     * 通过教师ID查询教师信息（含学历、职称、学科）
     * 
     * @param teacherId 教师ID
     * @return 教师信息
     */
    public TeaTeacher selectTeacherById(Long teacherId);

    /**
     * 校验工号是否唯一
     * 
     * @param teacher 教师信息
     * @return 结果
     */
    public boolean checkTeacherNoUnique(TeaTeacher teacher);

    /**
     * 新增教师信息
     * 
     * @param teacher 教师信息
     * @return 结果
     */
    public int insertTeacher(TeaTeacher teacher);

    /**
     * 修改教师信息
     * 
     * @param teacher 教师信息
     * @return 结果
     */
    public int updateTeacher(TeaTeacher teacher);

    /**
     * 批量删除教师信息
     * 
     * @param teacherIds 需要删除的教师ID
     * @return 结果
     */
    public int deleteTeacherByIds(Long[] teacherIds);
}
