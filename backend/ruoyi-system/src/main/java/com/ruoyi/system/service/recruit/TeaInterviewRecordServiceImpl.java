package com.ruoyi.system.service.recruit;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.recruit.TeaInterviewRecord;
import com.ruoyi.system.mapper.recruit.TeaInterviewRecordMapper;

@Service
public class TeaInterviewRecordServiceImpl implements ITeaInterviewRecordService
{
    @Autowired
    private TeaInterviewRecordMapper interviewRecordMapper;

    @Override
    public List<TeaInterviewRecord> selectInterviewRecordList(TeaInterviewRecord record) { return interviewRecordMapper.selectInterviewRecordList(record); }

    @Override
    public TeaInterviewRecord selectInterviewRecordById(Long interviewId) { return interviewRecordMapper.selectInterviewRecordById(interviewId); }

    @Override
    public int insertInterviewRecord(TeaInterviewRecord record)
    {
        calculateTotalScore(record);
        return interviewRecordMapper.insertInterviewRecord(record);
    }

    @Override
    public int updateInterviewRecord(TeaInterviewRecord record)
    {
        calculateTotalScore(record);
        return interviewRecordMapper.updateInterviewRecord(record);
    }

    @Override
    public int deleteInterviewRecordByIds(Long[] interviewIds) { return interviewRecordMapper.deleteInterviewRecordByIds(interviewIds); }

    /** 自动计算综合分 = (教学×0.4 + 学术×0.4 + 答辩×0.2) */
    private void calculateTotalScore(TeaInterviewRecord record)
    {
        BigDecimal teaching = record.getTeachingScore() != null ? record.getTeachingScore() : BigDecimal.ZERO;
        BigDecimal academic = record.getAcademicScore() != null ? record.getAcademicScore() : BigDecimal.ZERO;
        BigDecimal defense = record.getDefenseScore() != null ? record.getDefenseScore() : BigDecimal.ZERO;
        BigDecimal total = teaching.multiply(new BigDecimal("0.4"))
            .add(academic.multiply(new BigDecimal("0.4")))
            .add(defense.multiply(new BigDecimal("0.2")))
            .setScale(2, BigDecimal.ROUND_HALF_UP);
        record.setTotalScore(total);
    }
}
