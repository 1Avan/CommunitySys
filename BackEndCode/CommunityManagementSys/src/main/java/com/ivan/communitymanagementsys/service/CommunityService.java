package com.ivan.communitymanagementsys.service;

import com.ivan.communitymanagementsys.dto.CommunityPageQueryDTO;
import com.ivan.communitymanagementsys.entity.Community;
import com.ivan.communitymanagementsys.result.PageResult;

import java.util.List;

public interface CommunityService {

    PageResult getAllCommunity(CommunityPageQueryDTO communityPageQueryDTO);

    List<Community> findByName(String name);

    void deleteById(Long id);

    void addCommunity(Community community);

    void updateCommunity(Community community);
}
