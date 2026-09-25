package com.example.hb.dto;

import lombok.Data;

@Data
public class CommentAddDTO {
    private Long articleId;
    private String content;
    private Long parentId;
    private Long rootId;
    private String targetUserName;
}
