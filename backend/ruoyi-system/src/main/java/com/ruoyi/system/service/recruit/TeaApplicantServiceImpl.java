package com.ruoyi.system.service.recruit;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.recruit.TeaApplicant;
import com.ruoyi.system.domain.recruit.TeaApplicantEducation;
import com.ruoyi.system.domain.recruit.TeaApplicantWork;
import com.ruoyi.system.domain.recruit.TeaApplicantAchievement;
import com.ruoyi.system.domain.recruit.TeaInterviewRecord;
import com.ruoyi.system.mapper.recruit.TeaApplicantMapper;
import com.ruoyi.system.mapper.recruit.TeaApplicantEducationMapper;
import com.ruoyi.system.mapper.recruit.TeaApplicantWorkMapper;
import com.ruoyi.system.mapper.recruit.TeaApplicantAchievementMapper;
import com.ruoyi.system.mapper.recruit.TeaInterviewRecordMapper;

@Service
public class TeaApplicantServiceImpl implements ITeaApplicantService
{
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
    public int approveApplicant(Long applicantId, String targetStatus, String updateBy)
    {
        TeaApplicant applicant = new TeaApplicant();
        applicant.setApplicantId(applicantId);
        applicant.setStatus(targetStatus);
        applicant.setUpdateBy(updateBy);
        int rows = applicantMapper.updateApplicant(applicant);

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

        // 录用时更新引进计划的已录用人数
        if ("3".equals(targetStatus))
        {
            TeaApplicant full = applicantMapper.selectApplicantById(applicantId);
            if (full != null && full.getPlanId() != null)
            {
                // 此处可扩展更新计划的actual_count
            }
        }

        return rows;
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
