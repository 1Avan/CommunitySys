package com.ivan.communitymanagementsys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ivan.communitymanagementsys.controller.NewsletterController;
import com.ivan.communitymanagementsys.dto.NewsletterPageQueryDTO;
import com.ivan.communitymanagementsys.entity.Activity;
import com.ivan.communitymanagementsys.entity.Newsletter;
import com.ivan.communitymanagementsys.mapper.NewsletterMapper;
import com.ivan.communitymanagementsys.result.PageResult;
import com.ivan.communitymanagementsys.service.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class NewsletterServiceImpl implements NewsletterService {
    @Autowired
    private NewsletterMapper newsletterMapper;


    @Override
    public PageResult pageNewsletter(NewsletterPageQueryDTO newsletterPageQueryDTO) {
        PageHelper.startPage(newsletterPageQueryDTO.getPage(),newsletterPageQueryDTO.getPageSize());
        Page<Newsletter> page = newsletterMapper.getAllNewsletter(newsletterPageQueryDTO);
        Long total = page.getTotal();
        List<Newsletter> newsletters = page.getResult();
        return new PageResult(total,newsletters);
    }

    @Override
    public void addNewsletter(Newsletter newsletter) {
        newsletter.setPublishTime(LocalDateTime.now());
        newsletterMapper.insert(newsletter);
    }

    @Override
    public void updateNewsletter(Newsletter newsletter) {
        newsletter.setPublishTime(LocalDateTime.now());
        newsletterMapper.update(newsletter);
    }

    @Override
    public void deleteNewsletter(Long id) {
        newsletterMapper.delete(id);
    }

    @Override
    public List<Newsletter> randomGetNewsletter() {
        //获取所有快讯
        List<Newsletter> newsletterList  = newsletterMapper.getAllNewsletterNoArgs();
        if (newsletterList.size() <= 4) {
            return newsletterList; // 如果快讯数量少于或等于四个，则直接返回所有快讯
        }
        Random random = new Random();
        List<Newsletter> randomnewsletters = new ArrayList<>();
        while (randomnewsletters.size() < 4) {
            int index = random.nextInt(newsletterList.size());
            Newsletter newsletter = newsletterList.get(index);
            if (!randomnewsletters.contains(newsletter)) {
                randomnewsletters.add(newsletter);
            }
        }
        return randomnewsletters;
    }

    @Override
    public Newsletter getNewsletterById(Long id) {

        Newsletter newsletter = newsletterMapper.findById(id);
        return newsletter;
    }


}
