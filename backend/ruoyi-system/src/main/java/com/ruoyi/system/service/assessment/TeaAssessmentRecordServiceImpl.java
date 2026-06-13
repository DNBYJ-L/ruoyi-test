package com.ruoyi.system.service.assessment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.assessment.TeaAssessmentRecord;
import com.ruoyi.system.domain.assessment.TeaAssessmentTeaching;
import com.ruoyi.system.domain.assessment.TeaAssessmentResearch;
import com.ruoyi.system.domain.assessment.TeaAssessmentService;
import com.ruoyi.system.mapper.assessment.TeaAssessmentRecordMapper;
import com.ruoyi.system.mapper.assessment.TeaAssessmentTeachingMapper;
import com.ruoyi.system.mapper.assessment.TeaAssessmentResearchMapper;
import com.ruoyi.system.mapper.assessment.TeaAssessmentServiceMapper;

@Service
public class TeaAssessmentRecordServiceImpl implements ITeaAssessmentRecordService
{
    @Autowired
    private TeaAssessmentRecordMapper assessmentRecordMapper;
    @Autowired
    private TeaAssessmentTeachingMapper teachingMapper;
    @Autowired
    private TeaAssessmentResearchMapper researchMapper;
    @Autowired
    private TeaAssessmentServiceMapper serviceMapper;

    @Override
    public List<TeaAssessmentRecord> selectAssessmentRecordList(TeaAssessmentRecord record) { return assessmentRecordMapper.selectAssessmentRecordList(record); }

    @Override
    public TeaAssessmentRecord selectAssessmentRecordById(Long recordId)
    {
        TeaAssessmentRecord record = assessmentRecordMapper.selectAssessmentRecordById(recordId);
        if (record != null)
        {
            record.setTeachings(teachingMapper.selectByRecordId(recordId));
            record.setResearches(researchMapper.selectByRecordId(recordId));
            record.setServices(serviceMapper.selectByRecordId(recordId));
        }
        return record;
    }

    @Override
    @Transactional
    public int insertAssessmentRecord(TeaAssessmentRecord record)
    {
        calculateScore(record);
        int rows = assessmentRecordMapper.insertAssessmentRecord(record);
        insertSubTables(record);
        return rows;
    }

    @Override
    @Transactional
    public int updateAssessmentRecord(TeaAssessmentRecord record)
    {
        calculateScore(record);
        teachingMapper.deleteByRecordId(record.getRecordId());
        researchMapper.deleteByRecordId(record.getRecordId());
        serviceMapper.deleteByRecordId(record.getRecordId());
        insertSubTables(record);
        return assessmentRecordMapper.updateAssessmentRecord(record);
    }

    @Override
    @Transactional
    public int deleteAssessmentRecordByIds(Long[] recordIds)
    {
        for (Long id : recordIds)
        {
            teachingMapper.deleteByRecordId(id);
            researchMapper.deleteByRecordId(id);
            serviceMapper.deleteByRecordId(id);
        }
        return assessmentRecordMapper.deleteAssessmentRecordByIds(recordIds);
    }

    @Override
    public int submitRecord(Long recordId, String updateBy)
    {
        TeaAssessmentRecord record = new TeaAssessmentRecord();
        record.setRecordId(recordId);
        record.setStatus("1");
        record.setSubmitTime(new Date());
        record.setUpdateBy(updateBy);
        return assessmentRecordMapper.updateAssessmentRecord(record);
    }

    @Override
    public List<Map<String, Object>> selectScoreStat(Map<String, Object> params) { return assessmentRecordMapper.selectScoreStat(params); }

    private void calculateScore(TeaAssessmentRecord record)
    {
        BigDecimal teaching = record.getTeachingScore() != null ? record.getTeachingScore() : BigDecimal.ZERO;
        BigDecimal research = record.getResearchScore() != null ? record.getResearchScore() : BigDecimal.ZERO;
        BigDecimal service = record.getServiceScore() != null ? record.getServiceScore() : BigDecimal.ZERO;
        // 总分 = 教学×40% + 科研×40% + 服务×20%
        BigDecimal total = teaching.multiply(new BigDecimal("0.4"))
            .add(research.multiply(new BigDecimal("0.4")))
            .add(service.multiply(new BigDecimal("0.2")))
            .setScale(2, BigDecimal.ROUND_HALF_UP);
        record.setTotalScore(total);
        // 自动建议等级
        if (StringUtils.isEmpty(record.getGrade()))
        {
            if (total.compareTo(new BigDecimal("90")) >= 0) record.setGrade("优秀");
            else if (total.compareTo(new BigDecimal("70")) >= 0) record.setGrade("合格");
            else if (total.compareTo(new BigDecimal("60")) >= 0) record.setGrade("基本合格");
            else record.setGrade("不合格");
        }
    }

    private void insertSubTables(TeaAssessmentRecord record)
    {
        Long recordId = record.getRecordId();
        if (StringUtils.isNotEmpty(record.getTeachings()))
        {
            for (TeaAssessmentTeaching t : record.getTeachings())
            {
                if (StringUtils.isEmpty(t.getCourseName())) continue;
                t.setRecordId(recordId);
                teachingMapper.insert(t);
            }
        }
        if (StringUtils.isNotEmpty(record.getResearches()))
        {
            for (TeaAssessmentResearch r : record.getResearches())
            {
                if (StringUtils.isEmpty(r.getTitle())) continue;
                r.setRecordId(recordId);
                researchMapper.insert(r);
            }
        }
        if (StringUtils.isNotEmpty(record.getServices()))
        {
            for (TeaAssessmentService s : record.getServices())
            {
                if (StringUtils.isEmpty(s.getContent())) continue;
                s.setRecordId(recordId);
                serviceMapper.insert(s);
            }
        }
    }
}
