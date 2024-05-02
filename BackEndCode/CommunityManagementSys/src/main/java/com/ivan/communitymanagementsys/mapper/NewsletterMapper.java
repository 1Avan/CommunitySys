package com.ivan.communitymanagementsys.mapper;

import com.github.pagehelper.Page;
import com.ivan.communitymanagementsys.dto.NewsletterPageQueryDTO;
import com.ivan.communitymanagementsys.entity.Newsletter;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsletterMapper {
    Page<Newsletter> getAllNewsletter(NewsletterPageQueryDTO newsletterPageQueryDTO);

    @Insert("insert into newsletter(name, content, img,publish_time) VALUES (#{name},#{content},#{img},#{publishTime})")
    void insert(Newsletter newsletter);

    void update(Newsletter newsletter);

    @Delete("delete from newsletter where  id = #{id}")
    void delete(Long id);

    @Select("select * from newsletter")
    List<Newsletter> getAllNewsletterNoArgs();

    @Select("select * from newsletter where id = #{id}")
    Newsletter findById(Long id);
}
