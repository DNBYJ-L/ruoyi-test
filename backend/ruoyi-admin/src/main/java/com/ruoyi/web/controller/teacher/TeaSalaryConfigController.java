package com.ruoyi.web.controller.teacher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.teacher.TeaSalaryConfig;
import com.ruoyi.system.service.teacher.ITeaSalaryConfigService;

/**
 * 薪资配置 操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/teacher/salary-config")
public class TeaSalaryConfigController extends BaseController
{
    @Autowired
    private ITeaSalaryConfigService salaryConfigService;

    @PreAuthorize("@ss.hasPermi('teacher:salary:list')")
    @GetMapping("/list")
    public TableDataInfo list(TeaSalaryConfig config)
    {
        startPage();
        List<TeaSalaryConfig> list = salaryConfigService.selectSalaryConfigList(config);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('teacher:salary:query')")
    @GetMapping(value = "/{configId}")
    public AjaxResult getInfo(@PathVariable Long configId)
    {
        return success(salaryConfigService.selectSalaryConfigById(configId));
    }

    @PreAuthorize("@ss.hasPermi('teacher:salary:add')")
    @Log(title = "薪资配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TeaSalaryConfig config)
    {
        config.setCreateBy(getUsername());
        return toAjax(salaryConfigService.insertSalaryConfig(config));
    }

    @PreAuthorize("@ss.hasPermi('teacher:salary:edit')")
    @Log(title = "薪资配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TeaSalaryConfig config)
    {
        config.setUpdateBy(getUsername());
        return toAjax(salaryConfigService.updateSalaryConfig(config));
    }

    @PreAuthorize("@ss.hasPermi('teacher:salary:edit')")
    @Log(title = "薪资配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{configIds}")
    public AjaxResult remove(@PathVariable Long[] configIds)
    {
        return toAjax(salaryConfigService.deleteSalaryConfigByIds(configIds));
    }
}
