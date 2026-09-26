package com.example.hb.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 前端展示的评论所需要的字段
 */
@Data
public class CommentVO {
    private Long id;
    private Long articleId;
    private String userName;
    private String content;

    private Long parentId;
    private Long rootId;
    private String targetUserName;
    private String createTime;

    // 子评论，但是子评论不再有子评论，会根据 parentId 指定 父评论id
    private List<CommentVO> children = new ArrayList<>();
}
