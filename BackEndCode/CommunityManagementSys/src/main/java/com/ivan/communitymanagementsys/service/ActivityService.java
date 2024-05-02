package com.ivan.communitymanagementsys.service;

import com.ivan.communitymanagementsys.dto.ActivityDTO;
import com.ivan.communitymanagementsys.dto.ActivityPageQueryDTO;
import com.ivan.communitymanagementsys.entity.Activity;
import com.ivan.communitymanagementsys.result.PageResult;
import com.ivan.communitymanagementsys.result.Result;

import java.util.List;

public interface ActivityService {
    PageResult getAllActivity(ActivityPageQueryDTO activityPageQueryDTO);

    void addActivity(ActivityDTO activityDTO);

    void removeActivity(Long id);

    void updateActivity(ActivityDTO activityDTO);

    Activity getActivityById(Long id);

    List<Activity> getActivityByCid(Long communityId);

    List<Activity>  getRandomActivities();
}
