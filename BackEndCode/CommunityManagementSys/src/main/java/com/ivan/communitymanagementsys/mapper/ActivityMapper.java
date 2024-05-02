package com.ivan.communitymanagementsys.mapper;

import com.github.pagehelper.Page;
import com.ivan.communitymanagementsys.dto.ActivityDTO;
import com.ivan.communitymanagementsys.dto.ActivityPageQueryDTO;
import com.ivan.communitymanagementsys.entity.Activity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ActivityMapper {
    Page<Activity> findAllActivity(ActivityPageQueryDTO activityPageQueryDTO);

    @Insert("insert into activity(name, description, activity_place,community_id, start_time, end_time) VALUES " +
            "(#{name},#{description},#{activityPlace},#{communityId},#{startTime},#{endTime})")
    void insertActivity(ActivityDTO activityDTO);

    /**
     * 根据id删除活动，活动较多不做伪删除
     */
    @Delete("delete from activity where id = #{id}")
    void deleteById(Long id);

    void updateById(ActivityDTO activityDTO);

    @Select("select * from activity where id = #{id}")
    Activity findById(Long id);

    @Select("select * from activity where community_id = #{communityId}")
    List<Activity> findByCId(Long communityId);

    @Select("select * from activity")
    List<Activity> getAllActivities();

    @Select("select * from activity where end_time<#{now}")
    List<Activity> getByEndTime(LocalDateTime now);
}
