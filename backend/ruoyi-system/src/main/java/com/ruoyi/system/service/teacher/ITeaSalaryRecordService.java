package com.ruoyi.system.service.teacher;

import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.teacher.TeaSalaryRecord;

/**
 * 薪资记录 服务层
 * 
 * @author ruoyi
 */
public interface ITeaSalaryRecordService
{
    public List<TeaSalaryRecord> selectSalaryRecordList(TeaSalaryRecord record);
    public TeaSalaryRecord selectSalaryRecordById(Long recordId);
    public List<TeaSalaryRecord> selectSalaryRecordByTeacherId(Long teacherId);
    public int insertSalaryRecord(TeaSalaryRecord record);
    public int updateSalaryRecord(TeaSalaryRecord record);
    public int deleteSalaryRecordByIds(Long[] recordIds);
    /** 全校薪资KPI统计 */
    public Map<String, Object> selectSalaryKpi(Map<String, Object> params);
    /** 按院系统计薪资 */
    public List<Map<String, Object>> selectSalaryByDept(Map<String, Object> params);
    /** 批量生成指定月份的薪资记录 */
    public int batchGenerateSalary(int salaryYear, int salaryMonth, String createBy);
}
