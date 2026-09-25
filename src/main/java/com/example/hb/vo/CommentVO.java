package com.example.hb.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentVO {
    private Long id;
    private Long articleId;
    private Long parentId;
    private Long rootId;

    private String userName;
    private String content;
    private String createTime;
    private String targetUserName;

    private List<CommentVO> children = new ArrayList<>();
}
