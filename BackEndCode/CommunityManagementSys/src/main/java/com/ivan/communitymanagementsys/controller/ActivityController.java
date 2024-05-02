package com.ivan.communitymanagementsys.controller;

import com.ivan.communitymanagementsys.dto.ActivityDTO;
import com.ivan.communitymanagementsys.dto.ActivityPageQueryDTO;
import com.ivan.communitymanagementsys.entity.Activity;
import com.ivan.communitymanagementsys.result.PageResult;
import com.ivan.communitymanagementsys.result.Result;
import com.ivan.communitymanagementsys.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "活动相关")
@RequestMapping("/cms/activity")
@Slf4j
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    /**
     * 分页获取活动
     * @param activityPageQueryDTO
     * @return
     */
    @Operation(summary = "分页获取活动")
    @GetMapping("/page")
    public Result<PageResult> getAllActivity(ActivityPageQueryDTO activityPageQueryDTO){
        log.info("查询所有活动信息，{}",activityPageQueryDTO);
        PageResult pageResult = activityService.getAllActivity(activityPageQueryDTO);
        return Result.success(pageResult);
    }

//    社团获取本社团的活动
//    根据活动id获取活动信息
//    新增活动
    @Operation(summary = "新增活动")
    @PostMapping
    public Result addActivity(@RequestBody ActivityDTO activityDTO){
        log.info("添加活动，{}",activityDTO);
        activityService.addActivity(activityDTO);
        return Result.success();
    }
//    删除活动
    @Operation(summary = "删除活动")
    @DeleteMapping("/{id}")
    public Result removeActivity(@PathVariable Long id){
        activityService.removeActivity(id);
        return Result.success();
    }
//    修改活动
    @Operation(summary = "修改活动")
    @PutMapping
    public Result updateActivity(@RequestBody ActivityDTO activityDTO){
        log.info("修改活动，{}",activityDTO);
        activityService.updateActivity(activityDTO);
        return Result.success();
    }

    //根据id获取活动
    @Operation(summary = "根据id获取活动")
    @GetMapping("/{id}")
    public Result<Activity> getActivityById(@PathVariable Long id){
        Activity activity = activityService.getActivityById(id);
        return Result.success(activity);
    }

    //根据社团id获取活动
    @Operation(summary = "根据id获取活动")
    @GetMapping("/tocommunity/{communityId}")
    public Result<List<Activity>> getActivityByCid(@PathVariable Long communityId){
        List<Activity> activityList = activityService.getActivityByCid(communityId);
        return Result.success(activityList);
    }

    //随机抽取四个活动传递到后端
    @GetMapping("/random-activities")
    public Result<List<Activity>> getRandomActivities() {
        return Result.success(activityService.getRandomActivities());
    }


}
