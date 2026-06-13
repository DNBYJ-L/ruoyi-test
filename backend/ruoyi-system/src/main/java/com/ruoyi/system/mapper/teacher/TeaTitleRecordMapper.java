package com.ruoyi.system.mapper.teacher;

import java.util.List;
import com.ruoyi.system.domain.teacher.TeaTitleRecord;

/**
 * 教师职称记录 数据层
 * 
 * @author ruoyi
 */
public interface TeaTitleRecordMapper
{
    /**
     * 通过教师ID查询职称记录列表
     * 
     * @param teacherId 教师ID
     * @return 职称记录集合
     */
    public List<TeaTitleRecord> selectTitleRecordByTeacherId(Long teacherId);

    /**
     * 新增职称记录
     * 
     * @param record 职称记录
     * @return 结果
     */
    public int insertTitleRecord(TeaTitleRecord record);

    /**
     * 修改职称记录
     * 
     * @param record 职称记录
     * @return 结果
     */
    public int updateTitleRecord(TeaTitleRecord record);

    /**
     * 通过教师ID删除职称记录
     * 
     * @param teacherId 教师ID
     * @return 结果
     */
    public int deleteTitleRecordByTeacherId(Long teacherId);

    /**
     * 批量删除职称记录
     * 
     * @param teacherIds 教师ID数组
     * @return 结果
     */
    public int deleteTitleRecordByTeacherIds(Long[] teacherIds);
}
