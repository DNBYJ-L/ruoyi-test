package com.ruoyi.system.service.teacher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.teacher.TeaSalaryConfig;
import com.ruoyi.system.mapper.teacher.TeaSalaryConfigMapper;

/**
 * 薪资配置 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class TeaSalaryConfigServiceImpl implements ITeaSalaryConfigService
{
    @Autowired
    private TeaSalaryConfigMapper salaryConfigMapper;

    @Override
    public List<TeaSalaryConfig> selectSalaryConfigList(TeaSalaryConfig config)
    {
        return salaryConfigMapper.selectSalaryConfigList(config);
    }

    @Override
    public TeaSalaryConfig selectSalaryConfigById(Long configId)
    {
        return salaryConfigMapper.selectSalaryConfigById(configId);
    }

    @Override
    public TeaSalaryConfig selectSalaryConfigByTitleLevel(String titleLevel)
    {
        return salaryConfigMapper.selectSalaryConfigByTitleLevel(titleLevel);
    }

    @Override
    public int insertSalaryConfig(TeaSalaryConfig config)
    {
        return salaryConfigMapper.insertSalaryConfig(config);
    }

    @Override
    public int updateSalaryConfig(TeaSalaryConfig config)
    {
        return salaryConfigMapper.updateSalaryConfig(config);
    }

    @Override
    public int deleteSalaryConfigByIds(Long[] configIds)
    {
        return salaryConfigMapper.deleteSalaryConfigByIds(configIds);
    }
}
