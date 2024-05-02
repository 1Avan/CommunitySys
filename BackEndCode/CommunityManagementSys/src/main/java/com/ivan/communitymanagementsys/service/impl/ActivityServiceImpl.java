package com.ivan.communitymanagementsys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ivan.communitymanagementsys.dto.ActivityDTO;
import com.ivan.communitymanagementsys.dto.ActivityPageQueryDTO;
import com.ivan.communitymanagementsys.entity.Activity;
import com.ivan.communitymanagementsys.mapper.ActivityMapper;
import com.ivan.communitymanagementsys.result.PageResult;
import com.ivan.communitymanagementsys.result.Result;
import com.ivan.communitymanagementsys.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;
    @Override
    public PageResult getAllActivity(ActivityPageQueryDTO activityPageQueryDTO) {
        //开启分页查询
        PageHelper.startPage(activityPageQueryDTO.getPage(),activityPageQueryDTO.getPageSize());
        //将分页查询结果装入page
        Page<Activity> page = activityMapper.findAllActivity(activityPageQueryDTO);
        long total = page.getTotal();
        List<Activity> ActivityList = page.getResult();
        return new PageResult(total,ActivityList);
    }

    @Override
    public void addActivity(ActivityDTO activityDTO) {
        activityMapper.insertActivity(activityDTO);
    }

    @Override
    public void removeActivity(Long id) {
        activityMapper.deleteById(id);
    }

    @Override
    public void updateActivity(ActivityDTO activityDTO) {
        activityMapper.updateById(activityDTO);
    }

    @Override
    public Activity getActivityById(Long id) {
        Activity activity = activityMapper.findById(id);
        return activity;
    }

    @Override
    public List<Activity> getActivityByCid(Long communityId) {
        List<Activity> activityList = activityMapper.findByCId(communityId);

        return activityList;
    }

    @Override
    public List<Activity> getRandomActivities() {
        List<Activity> allActivities  = activityMapper.getAllActivities();
        if (allActivities.size() <= 4) {
            return allActivities; // 如果活动数量少于或等于四个，则直接返回所有活动
        }
        Random random = new Random();
        List<Activity> randomActivities = new ArrayList<>();
        while (randomActivities.size() < 4) {
            int index = random.nextInt(allActivities.size());
            Activity activity = allActivities.get(index);
            if (!randomActivities.contains(activity)) {
                randomActivities.add(activity);
            }
        }
        return randomActivities;
    }

}
