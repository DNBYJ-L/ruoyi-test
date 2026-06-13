package com.ruoyi.system.mapper.assessment;
import java.util.List;
import com.ruoyi.system.domain.assessment.TeaAssessmentTeaching;
public interface TeaAssessmentTeachingMapper {
    public List<TeaAssessmentTeaching> selectByRecordId(Long recordId);
    public int insert(TeaAssessmentTeaching teaching);
    public int deleteByRecordId(Long recordId);
}
