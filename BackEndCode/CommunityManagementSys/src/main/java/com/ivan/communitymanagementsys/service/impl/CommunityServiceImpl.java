package com.ivan.communitymanagementsys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ivan.communitymanagementsys.dto.CommunityPageQueryDTO;
import com.ivan.communitymanagementsys.entity.Community;
import com.ivan.communitymanagementsys.mapper.CommunityMapper;
import com.ivan.communitymanagementsys.result.PageResult;
import com.ivan.communitymanagementsys.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityMapper communityMapper;
    @Override
    public PageResult getAllCommunity(CommunityPageQueryDTO communityPageQueryDTO) {
        //开启分页查询
        PageHelper.startPage(communityPageQueryDTO.getPage(),communityPageQueryDTO.getPageSize());
        //将分页查询结果放入page
        Page<Community> page = communityMapper.pageQuery(communityPageQueryDTO);
        long total = page.getTotal();
        List<Community> communityList = page.getResult();
        return new PageResult(total,communityList);
    }

    @Override
    public List<Community> findByName(String name) {
        List<Community> communityList = communityMapper.searchByName();
        return communityList;
    }

    @Override
    public void deleteById(Long id) {
        communityMapper.deleteById(id);
    }

    @Override
    public void addCommunity(Community community) {
        communityMapper.insertCommunity(community);
    }

    @Override
    public void updateCommunity(Community community) {
        communityMapper.updateCommunity(community);
    }
}
