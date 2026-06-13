package com.ruoyi.web.controller.assessment;

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
import com.ruoyi.system.domain.assessment.TeaAssessmentRecord;
import com.ruoyi.system.service.assessment.ITeaAssessmentRecordService;

@RestController
@RequestMapping("/teacher/assessment-record")
public class TeaAssessmentRecordController extends BaseController
{
    @Autowired
    private ITeaAssessmentRecordService assessmentRecordService;

    @PreAuthorize("@ss.hasPermi('teacher:assess:list')")
    @GetMapping("/list")
    public TableDataInfo list(TeaAssessmentRecord record) { startPage(); return getDataTable(assessmentRecordService.selectAssessmentRecordList(record)); }

    @PreAuthorize("@ss.hasPermi('teacher:assess:query')")
    @GetMapping("/{recordId}")
    public AjaxResult getInfo(@PathVariable Long recordId) { return success(assessmentRecordService.selectAssessmentRecordById(recordId)); }

    @PreAuthorize("@ss.hasPermi('teacher:assess:add')")
    @Log(title = "考核填报", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TeaAssessmentRecord record) { record.setCreateBy(getUsername()); return toAjax(assessmentRecordService.insertAssessmentRecord(record)); }

    @PreAuthorize("@ss.hasPermi('teacher:assess:edit')")
    @Log(title = "考核填报", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TeaAssessmentRecord record) { record.setUpdateBy(getUsername()); return toAjax(assessmentRecordService.updateAssessmentRecord(record)); }

    @PreAuthorize("@ss.hasPermi('teacher:assess:remove')")
    @Log(title = "考核填报", businessType = BusinessType.DELETE)
    @DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds) { return toAjax(assessmentRecordService.deleteAssessmentRecordByIds(recordIds)); }

    @PreAuthorize("@ss.hasPermi('teacher:assess:edit')")
    @Log(title = "考核提交", businessType = BusinessType.UPDATE)
    @PostMapping("/submit/{recordId}")
    public AjaxResult submit(@PathVariable Long recordId) { return toAjax(assessmentRecordService.submitRecord(recordId, getUsername())); }

    @PreAuthorize("@ss.hasPermi('teacher:assess:list')")
    @GetMapping("/score-stat")
    public AjaxResult scoreStat(@RequestParam(required = false) Long planId)
    {
        Map<String, Object> params = new HashMap<>();
        if (planId != null) params.put("planId", planId);
        return success(assessmentRecordService.selectScoreStat(params));
    }
}
