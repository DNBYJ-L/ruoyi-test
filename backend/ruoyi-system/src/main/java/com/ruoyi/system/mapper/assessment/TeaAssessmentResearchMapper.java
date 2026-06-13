package com.ruoyi.system.mapper.assessment;
import java.util.List;
import com.ruoyi.system.domain.assessment.TeaAssessmentResearch;
public interface TeaAssessmentResearchMapper {
    public List<TeaAssessmentResearch> selectByRecordId(Long recordId);
    public int insert(TeaAssessmentResearch research);
    public int deleteByRecordId(Long recordId);
}
