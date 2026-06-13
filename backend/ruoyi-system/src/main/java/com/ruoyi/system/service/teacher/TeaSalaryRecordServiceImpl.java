package com.ruoyi.system.service.teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.teacher.TeaSalaryConfig;
import com.ruoyi.system.domain.teacher.TeaSalaryRecord;
import com.ruoyi.system.domain.teacher.TeaTeacher;
import com.ruoyi.system.mapper.teacher.TeaSalaryRecordMapper;
import com.ruoyi.system.mapper.teacher.TeaTeacherMapper;

/**
 * 薪资记录 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class TeaSalaryRecordServiceImpl implements ITeaSalaryRecordService
{
    @Autowired
    private TeaSalaryRecordMapper salaryRecordMapper;

    @Autowired
    private ITeaSalaryConfigService salaryConfigService;

    @Autowired
    private TeaTeacherMapper teacherMapper;

    @Autowired
    private com.ruoyi.system.mapper.teacher.TeaTitleRecordMapper titleRecordMapper;

    @Override
    public List<TeaSalaryRecord> selectSalaryRecordList(TeaSalaryRecord record)
    {
        return salaryRecordMapper.selectSalaryRecordList(record);
    }

    @Override
    public TeaSalaryRecord selectSalaryRecordById(Long recordId)
    {
        return salaryRecordMapper.selectSalaryRecordById(recordId);
    }

    @Override
    public List<TeaSalaryRecord> selectSalaryRecordByTeacherId(Long teacherId)
    {
        return salaryRecordMapper.selectSalaryRecordByTeacherId(teacherId);
    }

    @Override
    public int insertSalaryRecord(TeaSalaryRecord record)
    {
        // 根据教师当前职称查找薪资配置
        TeaSalaryConfig config = getConfigByTeacherId(record.getTeacherId());
        SalaryCalculator.calculate(record, config);
        return salaryRecordMapper.insertSalaryRecord(record);
    }

    @Override
    public int updateSalaryRecord(TeaSalaryRecord record)
    {
        TeaSalaryConfig config = getConfigByTeacherId(record.getTeacherId());
        SalaryCalculator.calculate(record, config);
        return salaryRecordMapper.updateSalaryRecord(record);
    }

    @Override
    public int deleteSalaryRecordByIds(Long[] recordIds)
    {
        return salaryRecordMapper.deleteSalaryRecordByIds(recordIds);
    }

    @Override
    public Map<String, Object> selectSalaryKpi(Map<String, Object> params)
    {
        return salaryRecordMapper.selectSalaryKpi(params);
    }

    @Override
    public List<Map<String, Object>> selectSalaryByDept(Map<String, Object> params)
    {
        return salaryRecordMapper.selectSalaryByDept(params);
    }

    @Override
    @Transactional
    public int batchGenerateSalary(int salaryYear, int salaryMonth, String createBy)
    {
        List<TeaTeacher> teachers = teacherMapper.selectActiveTeachersWithTitle();
        int count = 0;
        for (TeaTeacher teacher : teachers)
        {
            // 检查该月是否已有薪资记录
            Map<String, Object> params = new HashMap<>();
            params.put("teacherId", teacher.getTeacherId());
            params.put("salaryYear", salaryYear);
            params.put("salaryMonth", salaryMonth);
            if (salaryRecordMapper.selectExistingRecord(params) != null)
            {
                continue; // 已存在，跳过
            }

            TeaSalaryRecord record = new TeaSalaryRecord();
            record.setTeacherId(teacher.getTeacherId());
            record.setSalaryYear(salaryYear);
            record.setSalaryMonth(salaryMonth);
            record.setIssueStatus("0");
            record.setCreateBy(createBy);

            // 根据教师当前职称查找薪资配置并计算
            String titleName = teacher.getCurrentTitle();
            TeaSalaryConfig config = null;
            if (titleName != null && !titleName.isEmpty())
            {
                config = salaryConfigService.selectSalaryConfigByTitleLevel(titleName);
            }
            SalaryCalculator.calculate(record, config);
            salaryRecordMapper.insertSalaryRecord(record);
            count++;
        }
        return count;
    }

    /**
     * 根据教师ID获取其当前职称对应的薪资配置
     */
    private TeaSalaryConfig getConfigByTeacherId(Long teacherId)
    {
        if (teacherId == null) return null;
        // 直接查询该教师的职称记录，找当前职称
        List<com.ruoyi.system.domain.teacher.TeaTitleRecord> records = titleRecordMapper.selectTitleRecordByTeacherId(teacherId);
        for (com.ruoyi.system.domain.teacher.TeaTitleRecord record : records)
        {
            if ("1".equals(record.getIsCurrent()) && record.getTitleName() != null && !record.getTitleName().isEmpty())
            {
                return salaryConfigService.selectSalaryConfigByTitleLevel(record.getTitleName());
            }
        }
        return null;
    }
}
