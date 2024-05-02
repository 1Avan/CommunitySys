package com.ivan.communitymanagementsys.task;

import com.ivan.communitymanagementsys.constant.ApplicationsStatusConstant;
import com.ivan.communitymanagementsys.entity.ClubApplication;
import com.ivan.communitymanagementsys.mapper.ClubApplicationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class appliicationsTask {
    @Autowired
    private ClubApplicationMapper clubApplicationMapper;
    @Scheduled(cron = "0 0 0 * * *")//每天零点执行
    public void processAlreadyChuli (){
        log.info("处理已经审核的信息{}", LocalDateTime.now());
        // TODO 处理过期活动
        List<ClubApplication> clubApplicationList = clubApplicationMapper.getByStatus(ApplicationsStatusConstant.APPROVED,ApplicationsStatusConstant.REJECTED);
        if(clubApplicationList!=null && clubApplicationList.size()>0){
            for(ClubApplication clubApplication : clubApplicationList){
                //删除已审核信息
                clubApplicationMapper.deleteById(clubApplication.getId());
            }
        }

    }
}
