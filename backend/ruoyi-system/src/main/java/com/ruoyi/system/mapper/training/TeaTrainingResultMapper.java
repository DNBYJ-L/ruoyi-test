package com.ruoyi.system.mapper.training;
import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.training.TeaTrainingResult;
public interface TeaTrainingResultMapper {
    public List<TeaTrainingResult> selectTrainingResultList(TeaTrainingResult result);
    public TeaTrainingResult selectTrainingResultById(Long resultId);
    public int insertTrainingResult(TeaTrainingResult result);
    public int updateTrainingResult(TeaTrainingResult result);
    public List<Map<String, Object>> selectHoursStat(Map<String, Object> params);
}
