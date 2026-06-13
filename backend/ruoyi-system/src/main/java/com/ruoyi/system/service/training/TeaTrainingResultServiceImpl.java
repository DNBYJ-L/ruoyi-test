package com.ruoyi.system.service.training;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.training.TeaTrainingResult;
import com.ruoyi.system.mapper.training.TeaTrainingResultMapper;

@Service
public class TeaTrainingResultServiceImpl implements ITeaTrainingResultService
{
    @Autowired
    private TeaTrainingResultMapper trainingResultMapper;

    @Override
    public List<TeaTrainingResult> selectTrainingResultList(TeaTrainingResult result) { return trainingResultMapper.selectTrainingResultList(result); }

    @Override
    public TeaTrainingResult selectTrainingResultById(Long resultId) { return trainingResultMapper.selectTrainingResultById(resultId); }

    @Override
    public int insertTrainingResult(TeaTrainingResult result) { return trainingResultMapper.insertTrainingResult(result); }

    @Override
    public int updateTrainingResult(TeaTrainingResult result) { return trainingResultMapper.updateTrainingResult(result); }

    @Override
    public List<Map<String, Object>> selectHoursStat(Map<String, Object> params) { return trainingResultMapper.selectHoursStat(params); }
}
