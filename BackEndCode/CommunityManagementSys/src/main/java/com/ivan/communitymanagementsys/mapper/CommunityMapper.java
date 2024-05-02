package com.ivan.communitymanagementsys.mapper;

import com.github.pagehelper.Page;
import com.ivan.communitymanagementsys.dto.CommunityPageQueryDTO;
import com.ivan.communitymanagementsys.entity.Community;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper {
    Page<Community> pageQuery(CommunityPageQueryDTO communityPageQueryDTO);

    List<Community> searchByName();

    @Delete("delete from community where id = #{id}")
    void deleteById(Long id);

    @Insert("insert into community(community_name, description) VALUES (#{communityName},#{description})")
    void insertCommunity(Community community);

    void updateCommunity(Community community);
}
