package com.ruoyi.system.service.teacher;

import java.util.List;
import com.ruoyi.system.domain.teacher.TeaSalaryConfig;

/**
 * 薪资配置 服务层
 * 
 * @author ruoyi
 */
public interface ITeaSalaryConfigService
{
    public List<TeaSalaryConfig> selectSalaryConfigList(TeaSalaryConfig config);
    public TeaSalaryConfig selectSalaryConfigById(Long configId);
    public TeaSalaryConfig selectSalaryConfigByTitleLevel(String titleLevel);
    public int insertSalaryConfig(TeaSalaryConfig config);
    public int updateSalaryConfig(TeaSalaryConfig config);
    public int deleteSalaryConfigByIds(Long[] configIds);
}
