package com.ivan.communitymanagementsys.mapper;

import com.github.pagehelper.Page;
import com.ivan.communitymanagementsys.dto.UserPageQueryDTO;
import com.ivan.communitymanagementsys.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {


    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username};")
    User getByUsername(String username);


    /**
     * 插入用户
     * @param user
     */
    @Insert("insert into user(username, password, name, sex, phone, community_id, role_id, status) VALUES " +
            "(#{username},#{password},#{name},#{sex},#{phone},#{communityId},#{roleId},#{status})")
    void insert(User user);

    /**
     * 删除用户
     * @param id
     * @param status
     */
    @Update("update user set status = #{status} where id = #{id}")
    void logoff(Long id, Integer status);

    /**
     * 根据用户id更新用户
     * @param user
     */
    void update(User user);

    Page<User> findAll(UserPageQueryDTO userPageQueryDTO);

    @Select("select * from user where id = #{id}")
    User getUserById(Long id);

    @Delete("delete from user where id=#{id}")
    void deleteById(Long id);

    void updateUser(User user);

}
