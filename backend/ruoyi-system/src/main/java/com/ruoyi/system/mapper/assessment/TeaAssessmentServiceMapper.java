package com.ruoyi.system.mapper.assessment;
import java.util.List;
import com.ruoyi.system.domain.assessment.TeaAssessmentService;
public interface TeaAssessmentServiceMapper {
    public List<TeaAssessmentService> selectByRecordId(Long recordId);
    public int insert(TeaAssessmentService service);
    public int deleteByRecordId(Long recordId);
}
