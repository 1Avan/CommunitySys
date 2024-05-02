package com.ivan.communitymanagementsys.service;

import com.ivan.communitymanagementsys.dto.ActivityApplicationDTO;
import com.ivan.communitymanagementsys.entity.ActivityApplication;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;

public interface ActivityApplicationService {
    void sentActivityApplication(ActivityApplication activityApplication);

    void exportActivityXSLX(HttpServletResponse response, Long activityId);

    List<ActivityApplication> getUserList(Long userId);

    void deleteActivityForUser(ActivityApplicationDTO activityApplicationDTO);
}
