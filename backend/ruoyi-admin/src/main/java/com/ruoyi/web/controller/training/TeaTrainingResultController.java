package com.ruoyi.web.controller.training;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.training.TeaTrainingResult;
import com.ruoyi.system.service.training.ITeaTrainingResultService;

@RestController
@RequestMapping("/teacher/training-result")
public class TeaTrainingResultController extends BaseController
{
    @Autowired
    private ITeaTrainingResultService trainingResultService;

    @PreAuthorize("@ss.hasPermi('teacher:result:list')")
    @GetMapping("/list")
    public TableDataInfo list(TeaTrainingResult result) { startPage(); return getDataTable(trainingResultService.selectTrainingResultList(result)); }

    @PreAuthorize("@ss.hasPermi('teacher:result:query')")
    @GetMapping("/{resultId}")
    public AjaxResult getInfo(@PathVariable Long resultId) { return success(trainingResultService.selectTrainingResultById(resultId)); }

    @PreAuthorize("@ss.hasPermi('teacher:result:add')")
    @Log(title = "结业管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TeaTrainingResult result) { result.setCreateBy(getUsername()); return toAjax(trainingResultService.insertTrainingResult(result)); }

    @PreAuthorize("@ss.hasPermi('teacher:result:edit')")
    @Log(title = "结业管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TeaTrainingResult result) { result.setUpdateBy(getUsername()); return toAjax(trainingResultService.updateTrainingResult(result)); }

    @PreAuthorize("@ss.hasPermi('teacher:result:list')")
    @GetMapping("/hours-stat")
    public AjaxResult hoursStat(@RequestParam(required = false) Long teacherId)
    {
        Map<String, Object> params = new HashMap<>();
        if (teacherId != null) params.put("teacherId", teacherId);
        return success(trainingResultService.selectHoursStat(params));
    }
}
