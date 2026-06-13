package com.ruoyi.system.service.teacher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.teacher.TeaTeacher;
import com.ruoyi.system.domain.teacher.TeaEducation;
import com.ruoyi.system.domain.teacher.TeaTitleRecord;
import com.ruoyi.system.domain.teacher.TeaDiscipline;
import com.ruoyi.system.mapper.teacher.TeaTeacherMapper;
import com.ruoyi.system.mapper.teacher.TeaEducationMapper;
import com.ruoyi.system.mapper.teacher.TeaTitleRecordMapper;
import com.ruoyi.system.mapper.teacher.TeaDisciplineMapper;

/**
 * 教师信息 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class TeaTeacherServiceImpl implements ITeaTeacherService
{
    @Autowired
    private TeaTeacherMapper teacherMapper;

    @Autowired
    private TeaEducationMapper educationMapper;

    @Autowired
    private TeaTitleRecordMapper titleRecordMapper;

    @Autowired
    private TeaDisciplineMapper disciplineMapper;

    /**
     * 查询教师列表
     */
    @Override
    public List<TeaTeacher> selectTeacherList(TeaTeacher teacher)
    {
        return teacherMapper.selectTeacherList(teacher);
    }

    /**
     * 通过教师ID查询教师信息（含学历、职称、学科）
     */
    @Override
    public TeaTeacher selectTeacherById(Long teacherId)
    {
        TeaTeacher teacher = teacherMapper.selectTeacherById(teacherId);
        if (teacher != null)
        {
            teacher.setEducations(educationMapper.selectEducationByTeacherId(teacherId));
            teacher.setTitleRecords(titleRecordMapper.selectTitleRecordByTeacherId(teacherId));
            teacher.setDisciplines(disciplineMapper.selectDisciplineByTeacherId(teacherId));
        }
        return teacher;
    }

    /**
     * 校验工号是否唯一
     */
    @Override
    public boolean checkTeacherNoUnique(TeaTeacher teacher)
    {
        Long teacherId = teacher.getTeacherId() == null ? -1L : teacher.getTeacherId();
        TeaTeacher info = teacherMapper.checkTeacherNoUnique(teacher.getTeacherNo());
        if (StringUtils.isNotNull(info) && !info.getTeacherId().equals(teacherId))
        {
            return false;
        }
        return true;
    }

    /**
     * 新增教师信息（含子表联动保存）
     */
    @Override
    @Transactional
    public int insertTeacher(TeaTeacher teacher)
    {
        int rows = teacherMapper.insertTeacher(teacher);
        insertSubTables(teacher);
        return rows;
    }

    /**
     * 修改教师信息（含子表联动保存）
     */
    @Override
    @Transactional
    public int updateTeacher(TeaTeacher teacher)
    {
        // 先删后插子表
        educationMapper.deleteEducationByTeacherId(teacher.getTeacherId());
        titleRecordMapper.deleteTitleRecordByTeacherId(teacher.getTeacherId());
        disciplineMapper.deleteDisciplineByTeacherId(teacher.getTeacherId());
        insertSubTables(teacher);
        return teacherMapper.updateTeacher(teacher);
    }

    /**
     * 批量删除教师信息
     */
    @Override
    @Transactional
    public int deleteTeacherByIds(Long[] teacherIds)
    {
        educationMapper.deleteEducationByTeacherIds(teacherIds);
        titleRecordMapper.deleteTitleRecordByTeacherIds(teacherIds);
        disciplineMapper.deleteDisciplineByTeacherIds(teacherIds);
        return teacherMapper.deleteTeacherByIds(teacherIds);
    }

    /**
     * 插入子表数据
     */
    private void insertSubTables(TeaTeacher teacher)
    {
        Long teacherId = teacher.getTeacherId();
        // 保存学历信息（过滤空记录）
        List<TeaEducation> educations = teacher.getEducations();
        if (StringUtils.isNotEmpty(educations))
        {
            for (TeaEducation education : educations)
            {
                if (StringUtils.isEmpty(education.getEducationLevel()))
                {
                    continue;
                }
                education.setTeacherId(teacherId);
                education.setCreateBy(teacher.getCreateBy());
                educationMapper.insertEducation(education);
            }
        }
        // 保存职称记录（过滤空记录）
        List<TeaTitleRecord> titleRecords = teacher.getTitleRecords();
        if (StringUtils.isNotEmpty(titleRecords))
        {
            for (TeaTitleRecord record : titleRecords)
            {
                if (StringUtils.isEmpty(record.getTitleName()))
                {
                    continue;
                }
                record.setTeacherId(teacherId);
                record.setCreateBy(teacher.getCreateBy());
                titleRecordMapper.insertTitleRecord(record);
            }
        }
        // 保存学科信息（过滤空记录）
        List<TeaDiscipline> disciplines = teacher.getDisciplines();
        if (StringUtils.isNotEmpty(disciplines))
        {
            for (TeaDiscipline discipline : disciplines)
            {
                if (StringUtils.isEmpty(discipline.getFirstDiscipline()))
                {
                    continue;
                }
                discipline.setTeacherId(teacherId);
                discipline.setCreateBy(teacher.getCreateBy());
                disciplineMapper.insertDiscipline(discipline);
            }
        }
    }
}
