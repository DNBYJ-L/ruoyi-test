package com.ruoyi.web.controller.portal;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.ruoyi.system.domain.assessment.TeaAssessmentPlan;
import com.ruoyi.system.domain.assessment.TeaAssessmentRecord;
import com.ruoyi.system.service.assessment.ITeaAssessmentPlanService;
import com.ruoyi.system.service.assessment.ITeaAssessmentRecordService;
import com.ruoyi.system.service.assessment.ITeaAssessmentAuditService;
import com.ruoyi.system.utils.TeacherContext;

/**
 * 教师端 - 考核填报与结果
 */
@RestController
@RequestMapping("/portal/assessment")
public class PortalAssessmentController extends BaseController
{
    @Autowired
    private ITeaAssessmentPlanService assessmentPlanService;
    @Autowired
    private ITeaAssessmentRecordService assessmentRecordService;
    @Autowired
    private ITeaAssessmentAuditService assessmentAuditService;

    /**
     * 查看当前考核方案列表
     */
    @PreAuthorize("@ss.hasPermi('portal:assess:query')")
    @GetMapping("/plans")
    public TableDataInfo plans()
    {
        TeaAssessmentPlan query = new TeaAssessmentPlan();
        query.setStatus("1"); // 进行中
        startPage();
        List<TeaAssessmentPlan> list = assessmentPlanService.selectAssessmentPlanList(query);
        return getDataTable(list);
    }

    /**
     * 我的考核记录列表
     */
    @PreAuthorize("@ss.hasPermi('portal:assess:query')")
    @GetMapping("/records")
    public TableDataInfo records()
    {
        Long teacherId = TeacherContext.getCurrentTeacherId();
        TeaAssessmentRecord query = new TeaAssessmentRecord();
        query.setTeacherId(teacherId);
        startPage();
        List<TeaAssessmentRecord> list = assessmentRecordService.selectAssessmentRecordList(query);
        return getDataTable(list);
    }

    /**
     * 考核记录详情（含子表和审核轨迹）
     */
    @PreAuthorize("@ss.hasPermi('portal:assess:query')")
    @GetMapping("/record/{recordId}")
    public AjaxResult getRecord(@PathVariable Long recordId)
    {
        TeaAssessmentRecord record = assessmentRecordService.selectAssessmentRecordById(recordId);
        if (record != null)
        {
            Long teacherId = TeacherContext.getCurrentTeacherId();
            if (!teacherId.equals(record.getTeacherId()))
            {
                return error("无权查看该考核记录");
            }
        }
        AjaxResult result = success(record);
        result.put("auditTrail", assessmentAuditService.selectAuditTrail(recordId));
        return result;
    }

    /**
     * 新增考核填报
     */
    @PreAuthorize("@ss.hasPermi('portal:assess:edit')")
    @Log(title = "教师端-考核填报", businessType = BusinessType.INSERT)
    @PostMapping("/record")
    public AjaxResult addRecord(@RequestBody TeaAssessmentRecord record)
    {
        Long teacherId = TeacherContext.getCurrentTeacherId();
        record.setTeacherId(teacherId);
        record.setStatus("0"); // 暂存
        return toAjax(assessmentRecordService.insertAssessmentRecord(record));
    }

    /**
     * 修改考核填报（仅暂存/退回状态可修改）
     */
    @PreAuthorize("@ss.hasPermi('portal:assess:edit')")
    @Log(title = "教师端-考核填报", businessType = BusinessType.UPDATE)
    @PutMapping("/record")
    public AjaxResult editRecord(@RequestBody TeaAssessmentRecord record)
    {
        Long teacherId = TeacherContext.getCurrentTeacherId();
        // 校验归属
        TeaAssessmentRecord existing = assessmentRecordService.selectAssessmentRecordById(record.getRecordId());
        if (existing == null || !teacherId.equals(existing.getTeacherId()))
        {
            return error("无权修改该考核记录");
        }
        if (!"0".equals(existing.getStatus()) && !"5".equals(existing.getStatus()))
        {
            return error("当前状态不允许修改");
        }
        record.setTeacherId(teacherId);
        return toAjax(assessmentRecordService.updateAssessmentRecord(record));
    }

    /**
     * 提交审核
     */
    @PreAuthorize("@ss.hasPermi('portal:assess:submit')")
    @Log(title = "教师端-考核提交", businessType = BusinessType.UPDATE)
    @PostMapping("/submit/{recordId}")
    public AjaxResult submit(@PathVariable Long recordId)
    {
        Long teacherId = TeacherContext.getCurrentTeacherId();
        TeaAssessmentRecord existing = assessmentRecordService.selectAssessmentRecordById(recordId);
        if (existing == null || !teacherId.equals(existing.getTeacherId()))
        {
            return error("无权提交该考核记录");
        }
        return toAjax(assessmentRecordService.submitRecord(recordId, getUsername()));
    }
}
