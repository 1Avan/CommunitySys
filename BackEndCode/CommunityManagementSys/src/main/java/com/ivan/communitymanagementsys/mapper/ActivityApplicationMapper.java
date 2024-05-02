package com.ivan.communitymanagementsys.mapper;

import com.ivan.communitymanagementsys.dto.ActivityApplicationDTO;
import com.ivan.communitymanagementsys.entity.ActivityApplication;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivityApplicationMapper {
    @Insert("insert into activity_user (activity_id, user_id,application_time) VALUES (#{activityId},#{userId},#{applicationTime})")
    void insert(ActivityApplication activityApplication);

    @Select("select user_id from activity_user where activity_id = #{activityId}")
    List<Long> getByActivityId(Long activityId);

    @Select("select activity_id from activity_user where user_id=#{userId}")
    List<ActivityApplication> findByUserId(Long userId);

    @Delete("delete from activity_user where user_id= #{userId} and activity_id = #{activityId}")
    void delete(ActivityApplicationDTO activityApplicationDTO);
}
