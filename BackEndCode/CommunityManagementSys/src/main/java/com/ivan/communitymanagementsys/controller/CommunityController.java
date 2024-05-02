package com.ivan.communitymanagementsys.controller;

import com.ivan.communitymanagementsys.dto.CommunityPageQueryDTO;
import com.ivan.communitymanagementsys.entity.Community;
import com.ivan.communitymanagementsys.result.PageResult;
import com.ivan.communitymanagementsys.result.Result;
import com.ivan.communitymanagementsys.service.CommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "社团相关")
@RequestMapping("/cms/community")
@Slf4j
public class CommunityController {
    @Autowired
    private CommunityService communityService;

    //分页查询社团
    @GetMapping("/page")
    @Operation(summary = "分页查询社团")
    public Result<PageResult> getAllCommunity(CommunityPageQueryDTO communityPageQueryDTO){
        log.info("分页查询社团,{}",communityPageQueryDTO);
        PageResult pageResult = communityService.getAllCommunity(communityPageQueryDTO);
        return Result.success(pageResult);
    }

    //根据社团名模糊查询社团
    @GetMapping("/search/{name}")
    @Operation(summary = "根据社团名模糊查询社团")
    public Result<List<Community>> getByName(@PathVariable String name){
        log.info("根据社团名模糊查询社团,{}",name);
        List<Community> communityList = communityService.findByName(name);
        return Result.success(communityList);
    }
    //添加社团
    @Operation(summary = "添加社团")
    @PostMapping
    public Result addCommunity(@RequestBody Community community){
        log.info("添加社团,{}",community);
        communityService.addCommunity(community);
        return Result.success();
    }

    //删除社团
    @Operation(summary = "删除社团")
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id){
        log.info("删除社团，{}",id);
        communityService.deleteById(id);
        return Result.success();
    }

    //修改社团
    @Operation(summary = "修改社团")
    @PutMapping
    public Result updateCommunity(@RequestBody Community community){
        log.info("修改社团,{}",community);
        communityService.updateCommunity(community);
        return Result.success();
    }

}
