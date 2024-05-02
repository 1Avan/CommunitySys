package com.ivan.communitymanagementsys.task;

import com.ivan.communitymanagementsys.entity.Activity;
import com.ivan.communitymanagementsys.mapper.ActivityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class activityTask {
    @Autowired
    private ActivityMapper activityMapper;
//    @Scheduled(cron = "0 * * * *  ?")//每分钟触发一次
    @Scheduled(cron = "0 */5 * * * *")//每五分钟执行一次
    public void processTimeoutActivity(){
        log.info("处理过期活动{}", LocalDateTime.now());
        // TODO 处理过期活动
        List<Activity> activityList = activityMapper.getByEndTime(LocalDateTime.now());
        if(activityList!=null && activityList.size()>0){
            for(Activity activity : activityList){
                //删除过期活动
                activityMapper.deleteById(activity.getId());
            }
        }

    }
}
