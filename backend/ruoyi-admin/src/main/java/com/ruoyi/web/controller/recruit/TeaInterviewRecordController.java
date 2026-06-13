package com.ruoyi.web.controller.recruit;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.recruit.TeaInterviewRecord;
import com.ruoyi.system.service.recruit.ITeaInterviewRecordService;

@RestController
@RequestMapping("/teacher/interview")
public class TeaInterviewRecordController extends BaseController
{
    @Autowired
    private ITeaInterviewRecordService interviewRecordService;

    @PreAuthorize("@ss.hasPermi('teacher:interview:list')")
    @GetMapping("/list")
    public TableDataInfo list(TeaInterviewRecord record) { startPage(); return getDataTable(interviewRecordService.selectInterviewRecordList(record)); }

    @PreAuthorize("@ss.hasPermi('teacher:interview:query')")
    @GetMapping("/{interviewId}")
    public AjaxResult getInfo(@PathVariable Long interviewId) { return success(interviewRecordService.selectInterviewRecordById(interviewId)); }

    @PreAuthorize("@ss.hasPermi('teacher:interview:add')")
    @Log(title = "面试管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TeaInterviewRecord record) { record.setCreateBy(getUsername()); return toAjax(interviewRecordService.insertInterviewRecord(record)); }

    @PreAuthorize("@ss.hasPermi('teacher:interview:edit')")
    @Log(title = "面试管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TeaInterviewRecord record) { record.setUpdateBy(getUsername()); return toAjax(interviewRecordService.updateInterviewRecord(record)); }

    @PreAuthorize("@ss.hasPermi('teacher:interview:remove')")
    @Log(title = "面试管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{interviewIds}")
    public AjaxResult remove(@PathVariable Long[] interviewIds) { return toAjax(interviewRecordService.deleteInterviewRecordByIds(interviewIds)); }
}
