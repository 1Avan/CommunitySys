package com.ivan.communitymanagementsys.mapper;

import com.ivan.communitymanagementsys.dto.ApplicationDTO;
import com.ivan.communitymanagementsys.entity.ClubApplication;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClubApplicationMapper {
    @Select("select * from club_applications where community_id = #{communityId}")
    List<ClubApplication> findByCommunityId(Long communityId);

    @Insert("insert into club_applications(user_id, community_id, username,status, application_time, audit_time) VALUES (#{userId},#{communityId},#{username},#{status},#{applicationTime},#{auditTime})")
    void insert(ClubApplication clubApplication);

    void update(ApplicationDTO applicationDTO);

    @Select("select * from club_applications where user_id = #{userId}")
    List<ClubApplication> findByUserId(Long userId);

    @Select("select * from club_applications where status = #{approved} or status = #{rejected}")
    List<ClubApplication> getByStatus(Integer approved, Integer rejected);

    @Delete("delete from club_applications where id = #{id}")
    void deleteById(Long id);
}
