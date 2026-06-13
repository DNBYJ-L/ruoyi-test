package com.ruoyi.system.mapper.recruit;

import java.util.List;
import com.ruoyi.system.domain.recruit.TeaInterviewRecord;

public interface TeaInterviewRecordMapper
{
    public List<TeaInterviewRecord> selectInterviewRecordList(TeaInterviewRecord record);
    public TeaInterviewRecord selectInterviewRecordById(Long interviewId);
    public int insertInterviewRecord(TeaInterviewRecord record);
    public int updateInterviewRecord(TeaInterviewRecord record);
    public int deleteInterviewRecordByIds(Long[] interviewIds);
}
