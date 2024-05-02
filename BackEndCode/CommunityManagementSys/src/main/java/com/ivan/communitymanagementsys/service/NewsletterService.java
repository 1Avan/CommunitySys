package com.ivan.communitymanagementsys.service;

import com.ivan.communitymanagementsys.dto.NewsletterPageQueryDTO;
import com.ivan.communitymanagementsys.entity.Newsletter;
import com.ivan.communitymanagementsys.result.PageResult;

import java.util.List;

public interface NewsletterService {
    PageResult pageNewsletter(NewsletterPageQueryDTO newsletterPageQueryDTO);

    void addNewsletter(Newsletter newsletter);

    void updateNewsletter(Newsletter newsletter);

    void deleteNewsletter(Long id);

    List<Newsletter> randomGetNewsletter();

    Newsletter getNewsletterById(Long id);
}
