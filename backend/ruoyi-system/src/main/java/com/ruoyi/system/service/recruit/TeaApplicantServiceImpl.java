package com.ruoyi.system.service.recruit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.recruit.TeaApplicant;
import com.ruoyi.system.domain.recruit.TeaApplicantEducation;
import com.ruoyi.system.domain.recruit.TeaApplicantWork;
import com.ruoyi.system.domain.recruit.TeaApplicantAchievement;
import com.ruoyi.system.domain.recruit.TeaInterviewRecord;
import com.ruoyi.system.domain.teacher.TeaTeacher;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.recruit.TeaApplicantMapper;
import com.ruoyi.system.mapper.recruit.TeaApplicantEducationMapper;
import com.ruoyi.system.mapper.recruit.TeaApplicantWorkMapper;
import com.ruoyi.system.mapper.recruit.TeaApplicantAchievementMapper;
import com.ruoyi.system.mapper.recruit.TeaInterviewRecordMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.mapper.teacher.TeaTeacherMapper;

@Service
public class TeaApplicantServiceImpl implements ITeaApplicantService
{
    private static final String DEFAULT_PASSWORD = "123456";
    private static final Long TEACHER_ROLE_ID = 3L;

    @Autowired
    private TeaApplicantMapper applicantMapper;
    @Autowired
    private TeaApplicantEducationMapper educationMapper;
    @Autowired
    private TeaApplicantWorkMapper workMapper;
    @Autowired
    private TeaApplicantAchievementMapper achievementMapper;
    @Autowired
    private TeaInterviewRecordMapper interviewRecordMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private TeaTeacherMapper teaTeacherMapper;

    @Override
    public List<TeaApplicant> selectApplicantList(TeaApplicant applicant) { return applicantMapper.selectApplicantList(applicant); }

    @Override
    public TeaApplicant selectApplicantById(Long applicantId)
    {
        TeaApplicant applicant = applicantMapper.selectApplicantById(applicantId);
        if (applicant != null)
        {
            applicant.setEducations(educationMapper.selectEducationByApplicantId(applicantId));
            applicant.setWorks(workMapper.selectWorkByApplicantId(applicantId));
            applicant.setAchievements(achievementMapper.selectAchievementByApplicantId(applicantId));
        }
        return applicant;
    }

    @Override
    @Transactional
    public int insertApplicant(TeaApplicant applicant)
    {
        int rows = applicantMapper.insertApplicant(applicant);
        insertSubTables(applicant);
        return rows;
    }

    @Override
    @Transactional
    public int updateApplicant(TeaApplicant applicant)
    {
        educationMapper.deleteEducationByApplicantId(applicant.getApplicantId());
        workMapper.deleteWorkByApplicantId(applicant.getApplicantId());
        achievementMapper.deleteAchievementByApplicantId(applicant.getApplicantId());
        insertSubTables(applicant);
        return applicantMapper.updateApplicant(applicant);
    }

    @Override
    @Transactional
    public int deleteApplicantByIds(Long[] applicantIds)
    {
        for (Long id : applicantIds)
        {
            educationMapper.deleteEducationByApplicantId(id);
            workMapper.deleteWorkByApplicantId(id);
            achievementMapper.deleteAchievementByApplicantId(id);
        }
        return applicantMapper.deleteApplicantByIds(applicantIds);
    }

    @Override
    @Transactional
    public Map<String, Object> approveApplicant(Long applicantId, String targetStatus, String updateBy)
    {
        Map<String, Object> result = new HashMap<>();
        TeaApplicant applicant = new TeaApplicant();
        applicant.setApplicantId(applicantId);
        applicant.setStatus(targetStatus);
        applicant.setUpdateBy(updateBy);
        int rows = applicantMapper.updateApplicant(applicant);
        result.put("rows", rows);

        // 进入面试时自动创建面试记录
        if ("2".equals(targetStatus))
        {
            TeaInterviewRecord record = new TeaInterviewRecord();
            record.setApplicantId(applicantId);
            record.setInterviewType("综合面试");
            record.setInterviewDate(new Date());
            record.setResult("0"); // 待定
            record.setCreateBy(updateBy);
            interviewRecordMapper.insertInterviewRecord(record);
        }

        // 录用时自动生成教师账号
        if ("3".equals(targetStatus))
        {
            TeaApplicant full = applicantMapper.selectApplicantById(applicantId);
            if (full != null)
            {
                // 生成唯一用户名："26" + 2位随机数
                String userName = generateUniqueUserName();
                // 创建系统用户
                SysUser sysUser = new SysUser();
                sysUser.setUserName(userName);
                sysUser.setNickName(full.getName());
                sysUser.setPhonenumber(full.getPhone());
                sysUser.setEmail(full.getEmail());
                sysUser.setSex(full.getGender());
                sysUser.setPassword(SecurityUtils.encryptPassword(DEFAULT_PASSWORD));
                sysUser.setStatus("0");
                sysUser.setCreateBy(updateBy);
                sysUserMapper.insertUser(sysUser);

                // 分配教师角色
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(sysUser.getUserId());
                userRole.setRoleId(TEACHER_ROLE_ID);
                List<SysUserRole> userRoleList = new ArrayList<>();
                userRoleList.add(userRole);
                sysUserRoleMapper.batchUserRole(userRoleList);

                // 创建教师记录并关联用户
                TeaTeacher teacher = new TeaTeacher();
                teacher.setTeacherNo("T" + System.currentTimeMillis() % 100000);
                teacher.setTeacherName(full.getName());
                teacher.setGender(full.getGender());
                teacher.setBirthDate(full.getBirthDate());
                teacher.setPhone(full.getPhone());
                teacher.setEmail(full.getEmail());
                teacher.setUserId(sysUser.getUserId());
                teacher.setStatus("0");
                teacher.setCreateBy(updateBy);
                teaTeacherMapper.insertTeacher(teacher);

                // 返回生成的账号信息
                result.put("userName", userName);
                result.put("password", DEFAULT_PASSWORD);
                result.put("name", full.getName());
            }
        }

        return result;
    }

    /**
     * 生成唯一的用户名："26" + 2位随机数
     */
    private String generateUniqueUserName()
    {
        Random random = new Random();
        for (int i = 0; i < 100; i++)
        {
            String userName = "26" + String.format("%02d", random.nextInt(100));
            SysUser existing = sysUserMapper.checkUserNameUnique(userName);
            if (existing == null)
            {
                return userName;
            }
        }
        throw new ServiceException("无法生成唯一账号，请联系管理员");
    }

    private void insertSubTables(TeaApplicant applicant)
    {
        Long applicantId = applicant.getApplicantId();
        List<TeaApplicantEducation> educations = applicant.getEducations();
        if (StringUtils.isNotEmpty(educations))
        {
            for (TeaApplicantEducation edu : educations)
            {
                if (StringUtils.isEmpty(edu.getSchool())) continue;
                edu.setApplicantId(applicantId);
                educationMapper.insertApplicantEducation(edu);
            }
        }
        List<TeaApplicantWork> works = applicant.getWorks();
        if (StringUtils.isNotEmpty(works))
        {
            for (TeaApplicantWork work : works)
            {
                if (StringUtils.isEmpty(work.getUnit())) continue;
                work.setApplicantId(applicantId);
                workMapper.insertApplicantWork(work);
            }
        }
        List<TeaApplicantAchievement> achievements = applicant.getAchievements();
        if (StringUtils.isNotEmpty(achievements))
        {
            for (TeaApplicantAchievement ach : achievements)
            {
                if (StringUtils.isEmpty(ach.getTitle())) continue;
                ach.setApplicantId(applicantId);
                achievementMapper.insertApplicantAchievement(ach);
            }
        }
    }
}
