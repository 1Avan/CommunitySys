package com.ivan.communitymanagementsys.controller;

import com.ivan.communitymanagementsys.dto.ActivityApplicationDTO;
import com.ivan.communitymanagementsys.dto.ApplicationDTO;
import com.ivan.communitymanagementsys.entity.ActivityApplication;
import com.ivan.communitymanagementsys.entity.ClubApplication;
import com.ivan.communitymanagementsys.result.Result;
import com.ivan.communitymanagementsys.service.ActivityApplicationService;
import com.ivan.communitymanagementsys.service.ClubApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * 申请相关控制器
 */
@RestController
@RequestMapping("/cms/application")
@Tag(name="申请审批相关接口")
@Slf4j
public class ApplicationController {
    @Autowired
    private ClubApplicationService clubApplicationService;
    @Autowired
    private ActivityApplicationService activityApplicationService;
    //社长根据社团id获取申请信息
    @GetMapping("/{communityId}")
    @Operation(summary = "根据社团id获取信息")
    public Result<List<ClubApplication>> getClubApplication(@PathVariable Long communityId){
        log.info("根据社团id,{}",communityId);
        List<ClubApplication> clubApplication = clubApplicationService.getClubApplication(communityId);
        return Result.success(clubApplication);
    }

    //用户申请加入社团
    @PostMapping
    @Operation(summary = "用户申请加入社团")
    public Result sentClubApplication(@RequestBody ClubApplication clubApplication){
        log.info("用户发送申请信息，{}",clubApplication);
        clubApplicationService.sentClubApplication(clubApplication);
        return Result.success();
    }

    //社长根据用户id审核用户申请（改变审核状态即可）
    @PutMapping("/update")
    @Operation(summary = "社长根据用户id审核用户申请")
    public Result updateStatus(@RequestBody ApplicationDTO applicationDTO){
        log.info("社长根据用户id审核用户申请{}",applicationDTO);
        clubApplicationService.updateStatus(applicationDTO);
        return Result.success();
    }
    //根据用户id查询有关用户个人申请记录
    @GetMapping("/useroneself/{userId}")
    @Operation(summary = "根据用户id查询有关用户个人申请记录")
    public Result<List<ClubApplication>> getOnesApplications(@PathVariable Long userId){
        log.info("根据用户id查询有关用户个人申请记录,{}",userId);
        List<ClubApplication> applications = clubApplicationService.findByUserId(userId);
        return Result.success(applications);
    }

    //用户参加活动
    @PostMapping("/activity")
    @Operation(summary = "用户参加活动")
    public Result sentActivityApplication(@RequestBody ActivityApplication activityApplication){
        log.info("用户参加活动,{}",activityApplication);
        activityApplicationService.sentActivityApplication(activityApplication);
        return Result.success();
    }

    //导出某活动成员表
    @GetMapping("/activity/{activityId}")
    @Operation(summary = "导出某活动成员表")
    public Result exportActivityXSLX(HttpServletResponse response,@PathVariable Long activityId){
        log.info("导出某活动成员表,{}",activityId);
        activityApplicationService.exportActivityXSLX(response,activityId);
        return Result.success();
    }

    //根据用户id获取参加的互动列表
    @GetMapping("/activity")
    @Operation(summary = "根据用户id获取参加的活动列表")
    public Result<List<ActivityApplication>> getUserList(@RequestParam Long userId){
        log.info("根据用户id获取参加的活动列表,{}",userId);
        List<ActivityApplication> activityList = activityApplicationService.getUserList(userId);
        return Result.success(activityList);
    }

    //根据用户id及活动id删除活动
    @PostMapping("/activity/delete")
    @Operation(summary = "根据用户id及活动id删除活动")
    public Result deleteActivityForUser(@RequestBody ActivityApplicationDTO activityApplicationDTO){
        log.info("根据用户id及活动id删除活动{}",activityApplicationDTO);
        activityApplicationService.deleteActivityForUser(activityApplicationDTO);
        return Result.success();
    }


}
