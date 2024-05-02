package com.ivan.communitymanagementsys.service.impl;

import com.ivan.communitymanagementsys.dto.ApplicationDTO;
import com.ivan.communitymanagementsys.entity.ClubApplication;
import com.ivan.communitymanagementsys.mapper.ClubApplicationMapper;
import com.ivan.communitymanagementsys.service.ClubApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClubApplicationServiceImpl implements ClubApplicationService {
    @Autowired
    private ClubApplicationMapper clubApplicationMapper;
    @Override
    public List<ClubApplication> getClubApplication(Long communityId) {
       List<ClubApplication> clubApplications =  clubApplicationMapper.findByCommunityId(communityId);
       return clubApplications;
    }

    @Override
    public void sentClubApplication(ClubApplication clubApplication) {
        //审核状态默认是审核中
        //申请时间就是当前操作时间
        clubApplication.setApplicationTime(LocalDateTime.now());
        clubApplication.setStatus(0);
        clubApplicationMapper.insert(clubApplication);
    }

    @Override
    public void updateStatus(ApplicationDTO applicationDTO) {
        //审核时间就是当前操作时间
        applicationDTO.setAuditTime(LocalDateTime.now());
        clubApplicationMapper.update(applicationDTO);
    }

    @Override
    public List<ClubApplication> findByUserId(Long userId) {
        List<ClubApplication> applications = clubApplicationMapper.findByUserId(userId);
        return applications;
    }
}
