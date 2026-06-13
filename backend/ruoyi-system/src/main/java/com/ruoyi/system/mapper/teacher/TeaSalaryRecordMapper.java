package com.ruoyi.system.mapper.teacher;

import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.teacher.TeaSalaryRecord;

/**
 * 薪资记录 数据层
 * 
 * @author ruoyi
 */
public interface TeaSalaryRecordMapper
{
    public List<TeaSalaryRecord> selectSalaryRecordList(TeaSalaryRecord record);
    public TeaSalaryRecord selectSalaryRecordById(Long recordId);
    public List<TeaSalaryRecord> selectSalaryRecordByTeacherId(Long teacherId);
    public int insertSalaryRecord(TeaSalaryRecord record);
    public int updateSalaryRecord(TeaSalaryRecord record);
    public int deleteSalaryRecordById(Long recordId);
    public int deleteSalaryRecordByIds(Long[] recordIds);
    /** 统计全校薪资KPI */
    public Map<String, Object> selectSalaryKpi(Map<String, Object> params);
    /** 按院系统计薪资 */
    public List<Map<String, Object>> selectSalaryByDept(Map<String, Object> params);
    /** 查询指定教师指定月份的薪资记录是否存在 */
    public TeaSalaryRecord selectExistingRecord(Map<String, Object> params);
}
