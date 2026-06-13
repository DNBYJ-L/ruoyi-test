package com.ruoyi.web.controller.teacher;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.teacher.TeaTeacher;
import com.ruoyi.system.service.teacher.ITeaTeacherService;

/**
 * 教师信息管理 操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/teacher/info")
public class TeaTeacherController extends BaseController
{
    @Autowired
    private ITeaTeacherService teacherService;

    /**
     * 获取教师列表
     */
    @PreAuthorize("@ss.hasPermi('teacher:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(TeaTeacher teacher)
    {
        startPage();
        List<TeaTeacher> list = teacherService.selectTeacherList(teacher);
        return getDataTable(list);
    }

    /**
     * 导出教师列表
     */
    @Log(title = "教师管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('teacher:info:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, TeaTeacher teacher)
    {
        List<TeaTeacher> list = teacherService.selectTeacherList(teacher);
        ExcelUtil<TeaTeacher> util = new ExcelUtil<TeaTeacher>(TeaTeacher.class);
        util.exportExcel(response, list, "教师数据");
    }

    /**
     * 根据教师ID获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('teacher:info:query')")
    @GetMapping(value = "/{teacherId}")
    public AjaxResult getInfo(@PathVariable Long teacherId)
    {
        return success(teacherService.selectTeacherById(teacherId));
    }

    /**
     * 新增教师
     */
    @PreAuthorize("@ss.hasPermi('teacher:info:add')")
    @Log(title = "教师管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TeaTeacher teacher)
    {
        if (!teacherService.checkTeacherNoUnique(teacher))
        {
            return error("新增教师'" + teacher.getTeacherNo() + "'失败，工号已存在");
        }
        teacher.setCreateBy(getUsername());
        return toAjax(teacherService.insertTeacher(teacher));
    }

    /**
     * 修改教师
     */
    @PreAuthorize("@ss.hasPermi('teacher:info:edit')")
    @Log(title = "教师管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TeaTeacher teacher)
    {
        if (!teacherService.checkTeacherNoUnique(teacher))
        {
            return error("修改教师'" + teacher.getTeacherNo() + "'失败，工号已存在");
        }
        teacher.setUpdateBy(getUsername());
        return toAjax(teacherService.updateTeacher(teacher));
    }

    /**
     * 删除教师
     */
    @PreAuthorize("@ss.hasPermi('teacher:info:remove')")
    @Log(title = "教师管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{teacherIds}")
    public AjaxResult remove(@PathVariable Long[] teacherIds)
    {
        return toAjax(teacherService.deleteTeacherByIds(teacherIds));
    }

    /**
     * 校验工号是否重复
     */
    @PreAuthorize("@ss.hasPermi('teacher:info:query')")
    @GetMapping("/checkTeacherNo")
    public AjaxResult checkTeacherNo(TeaTeacher teacher)
    {
        return success(teacherService.checkTeacherNoUnique(teacher));
    }
}
