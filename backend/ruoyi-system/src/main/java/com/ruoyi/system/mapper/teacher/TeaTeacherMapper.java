package com.ruoyi.system.mapper.teacher;

import java.util.List;
import com.ruoyi.system.domain.teacher.TeaTeacher;

/**
 * 教师信息 数据层
 * 
 * @author ruoyi
 */
public interface TeaTeacherMapper
{
    /**
     * 查询教师列表
     * 
     * @param teacher 教师信息
     * @return 教师集合
     */
    public List<TeaTeacher> selectTeacherList(TeaTeacher teacher);

    /**
     * 通过教师ID查询教师信息
     * 
     * @param teacherId 教师ID
     * @return 教师信息
     */
    public TeaTeacher selectTeacherById(Long teacherId);

    /**
     * 通过工号查询教师信息
     * 
     * @param teacherNo 工号
     * @return 教师信息
     */
    public TeaTeacher checkTeacherNoUnique(String teacherNo);

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
     * 删除教师信息
     * 
     * @param teacherId 教师ID
     * @return 结果
     */
    public int deleteTeacherById(Long teacherId);

    /**
     * 批量删除教师信息
     * 
     * @param teacherIds 需要删除的教师ID
     * @return 结果
     */
    public int deleteTeacherByIds(Long[] teacherIds);

    /**
     * 查询所有在职教师（含当前职称）
     * 
     * @return 教师集合
     */
    public List<TeaTeacher> selectActiveTeachersWithTitle();
}
