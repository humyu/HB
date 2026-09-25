package com.example.hb.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Comment {
    private Long id;
    private Long articleId;
    private String userName;
    private String content;

    private Long parentId;
    private Long rootId;   // 根评论
    private String targetUserName;  // 被回复人的名字

    private LocalDateTime createTime;


}
