package com.ivan.communitymanagementsys.service;

import com.ivan.communitymanagementsys.dto.ApplicationDTO;
import com.ivan.communitymanagementsys.entity.ClubApplication;

import java.util.List;

public interface ClubApplicationService {
    List<ClubApplication> getClubApplication(Long communityId);

    void sentClubApplication(ClubApplication clubApplication);

    void updateStatus(ApplicationDTO applicationDTO);

    List<ClubApplication> findByUserId(Long userId);
}
