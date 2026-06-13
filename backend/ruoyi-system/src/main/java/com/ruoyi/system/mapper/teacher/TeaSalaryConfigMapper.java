package com.ruoyi.system.mapper.teacher;

import java.util.List;
import com.ruoyi.system.domain.teacher.TeaSalaryConfig;

/**
 * 薪资配置 数据层
 * 
 * @author ruoyi
 */
public interface TeaSalaryConfigMapper
{
    public List<TeaSalaryConfig> selectSalaryConfigList(TeaSalaryConfig config);
    public TeaSalaryConfig selectSalaryConfigById(Long configId);
    public TeaSalaryConfig selectSalaryConfigByTitleLevel(String titleLevel);
    public int insertSalaryConfig(TeaSalaryConfig config);
    public int updateSalaryConfig(TeaSalaryConfig config);
    public int deleteSalaryConfigById(Long configId);
    public int deleteSalaryConfigByIds(Long[] configIds);
}
