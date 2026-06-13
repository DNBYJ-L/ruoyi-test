package com.ruoyi.web.controller.teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.teacher.TeaSalaryRecord;
import com.ruoyi.system.service.teacher.ITeaSalaryRecordService;

/**
 * 薪资记录 操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/teacher/salary")
public class TeaSalaryRecordController extends BaseController
{
    @Autowired
    private ITeaSalaryRecordService salaryRecordService;

    /**
     * 获取薪资记录列表
     */
    @PreAuthorize("@ss.hasPermi('teacher:salary:list')")
    @GetMapping("/list")
    public TableDataInfo list(TeaSalaryRecord record)
    {
        startPage();
        List<TeaSalaryRecord> list = salaryRecordService.selectSalaryRecordList(record);
        return getDataTable(list);
    }

    /**
     * 导出薪资记录
     */
    @Log(title = "薪资管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('teacher:salary:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, TeaSalaryRecord record)
    {
        List<TeaSalaryRecord> list = salaryRecordService.selectSalaryRecordList(record);
        ExcelUtil<TeaSalaryRecord> util = new ExcelUtil<TeaSalaryRecord>(TeaSalaryRecord.class);
        util.exportExcel(response, list, "薪资数据");
    }

    /**
     * 获取薪资记录详情
     */
    @PreAuthorize("@ss.hasPermi('teacher:salary:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable Long recordId)
    {
        return success(salaryRecordService.selectSalaryRecordById(recordId));
    }

    /**
     * 获取教师个人薪资记录
     */
    @PreAuthorize("@ss.hasPermi('teacher:salary:query')")
    @GetMapping("/personal/{teacherId}")
    public AjaxResult personal(@PathVariable Long teacherId)
    {
        return success(salaryRecordService.selectSalaryRecordByTeacherId(teacherId));
    }

    /**
     * 新增薪资记录
     */
    @PreAuthorize("@ss.hasPermi('teacher:salary:add')")
    @Log(title = "薪资管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TeaSalaryRecord record)
    {
        record.setCreateBy(getUsername());
        return toAjax(salaryRecordService.insertSalaryRecord(record));
    }

    /**
     * 修改薪资记录
     */
    @PreAuthorize("@ss.hasPermi('teacher:salary:edit')")
    @Log(title = "薪资管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TeaSalaryRecord record)
    {
        record.setUpdateBy(getUsername());
        return toAjax(salaryRecordService.updateSalaryRecord(record));
    }

    /**
     * 删除薪资记录
     */
    @PreAuthorize("@ss.hasPermi('teacher:salary:edit')")
    @Log(title = "薪资管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(salaryRecordService.deleteSalaryRecordByIds(recordIds));
    }

    /**
     * 获取全校薪资KPI统计
     */
    @PreAuthorize("@ss.hasPermi('teacher:salary:list')")
    @GetMapping("/kpi")
    public AjaxResult kpi(@RequestParam(required = false) Integer salaryYear)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("salaryYear", salaryYear);
        return success(salaryRecordService.selectSalaryKpi(params));
    }

    /**
     * 获取院系薪资对比统计
     */
    @PreAuthorize("@ss.hasPermi('teacher:salary:list')")
    @GetMapping("/dept-stat")
    public AjaxResult deptStat(@RequestParam(required = false) Integer salaryYear)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("salaryYear", salaryYear);
        return success(salaryRecordService.selectSalaryByDept(params));
    }

    /**
     * 批量生成薪资记录
     */
    @PreAuthorize("@ss.hasPermi('teacher:salary:add')")
    @Log(title = "薪资管理", businessType = BusinessType.INSERT)
    @PostMapping("/batch-generate")
    public AjaxResult batchGenerate(@RequestParam int salaryYear, @RequestParam int salaryMonth)
    {
        int count = salaryRecordService.batchGenerateSalary(salaryYear, salaryMonth, getUsername());
        return success("成功生成 " + count + " 条薪资记录");
    }
}
