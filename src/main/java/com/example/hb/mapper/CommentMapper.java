package com.example.hb.mapper;

import com.example.hb.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("SELECT id, article_id, user_name, content, parent_id, root_id, target_user_name, create_time " +
            "FROM `comment` WHERE article_id = #{articleId} " +
            "ORDER BY create_time ASC, id ASC")
    List<Comment> findByArticleIdOrderByCreateTimeAsc(Long articleId);

    @Select("SELECT id, article_id, user_name, content, parent_id, root_id, target_user_name, create_time " +
            "FROM `comment` WHERE id = #{id}")
    Comment findById(Long id);

    @Insert("INSERT INTO `comment` " +
            "(article_id, user_name, content, parent_id, root_id, target_user_name, create_time) " +
            "VALUES " +
            "(#{articleId}, #{userName}, #{content}, #{parentId}, #{rootId}, #{targetUserName}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Comment comment);
}
