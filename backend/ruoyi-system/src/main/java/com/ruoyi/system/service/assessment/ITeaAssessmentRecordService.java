package com.ruoyi.system.service.assessment;
import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.assessment.TeaAssessmentRecord;
public interface ITeaAssessmentRecordService {
    public List<TeaAssessmentRecord> selectAssessmentRecordList(TeaAssessmentRecord record);
    public TeaAssessmentRecord selectAssessmentRecordById(Long recordId);
    public int insertAssessmentRecord(TeaAssessmentRecord record);
    public int updateAssessmentRecord(TeaAssessmentRecord record);
    public int deleteAssessmentRecordByIds(Long[] recordIds);
    public int submitRecord(Long recordId, String updateBy);
    public List<Map<String, Object>> selectScoreStat(Map<String, Object> params);
}
