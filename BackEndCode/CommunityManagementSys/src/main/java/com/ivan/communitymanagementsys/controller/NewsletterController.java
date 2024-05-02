package com.ivan.communitymanagementsys.controller;

import com.ivan.communitymanagementsys.dto.NewsletterPageQueryDTO;
import com.ivan.communitymanagementsys.entity.Newsletter;
import com.ivan.communitymanagementsys.mapper.NewsletterMapper;
import com.ivan.communitymanagementsys.result.PageResult;
import com.ivan.communitymanagementsys.result.Result;
import com.ivan.communitymanagementsys.service.NewsletterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "快讯相关")
@RequestMapping("/cms/newsletter")
@Slf4j
public class NewsletterController {
    @Autowired
    private NewsletterService newsletterService;
    /**
     * 分页查询快讯
     * @param newsletterPageQueryDTO
     * @return
     */
    @GetMapping("page")
    @Operation(summary = "分页查询快讯")
    public PageResult pageNewsletter(NewsletterPageQueryDTO newsletterPageQueryDTO){
        log.info("分页查询快讯{}",newsletterPageQueryDTO);
        PageResult  pageResult = newsletterService.pageNewsletter(newsletterPageQueryDTO);
        return pageResult;
    }

    /**
     * 新增快讯
     * @param newsletter
     * @return
     */
    @PostMapping
    @Operation(summary = "新增快讯")
    public Result addNewsletter(@RequestBody Newsletter newsletter){
        log.info("新增快讯{}",newsletter);
        newsletterService.addNewsletter(newsletter);
        return Result.success();
    }


    /**
     * 更新快讯
     * @param newsletter
     * @return
     */
    @PutMapping
    @Operation(summary = "更新快讯")
    public Result updateNewsletter(@RequestBody Newsletter newsletter){
        log.info("更新快讯{}",newsletter);
        newsletterService.updateNewsletter(newsletter);
        return Result.success();
    }

    /**
     * 删除快讯
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除快讯")
    public Result deleteNewsletter(@PathVariable Long id){
        log.info("删除快讯{}",id);
        newsletterService.deleteNewsletter(id);
        return Result.success();
    }


    /**
     * 随机获取四个快讯
     * @return
     */
    @GetMapping("/random")
    @Operation(summary = "随机获取四个快讯")
    public Result<List<Newsletter>> updateNewsletter(){
        log.info("随机获取四个快讯");
        List<Newsletter> newsletters = newsletterService.randomGetNewsletter();
        return Result.success(newsletters);
    }


    /**
     * 根据快讯id获取快讯信息
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据快讯id获取快讯信息")
    public Result<Newsletter> getNewsletterById(@PathVariable Long id){
        log.info("根据快讯id获取快讯信息");
        Newsletter newsletter= newsletterService.getNewsletterById(id);
        return Result.success(newsletter);
    }


}
