## 简单的评论系统

- 1.必要的字段
```
create table `comment` (

`id` bigint auto_increment primary key,
`article_id` bigint not null comment '文章id',
`user_name` varchar(20) not null comment '评论者(临时账号)',
`content` text not null comment '评论内容',
`create_time` datetime default current_timestamp comment '创建时间',

`parent_id` bigint default null comment '直接父评论id(回复了哪条评论)',
`root_id` bigint default null comment '所属的一级评论id',
`target_user_name` varchar(20) default null comment '被回复的用户名(用于展示 A回复B)'

);
```

对应的类
```java
@Data
public class Comment {
    private Long id;
    private Long articleId;
    private String userName;
    private String content;
    private LocalDateTime createTime;

    private Long parentId;  // 直接父评论id(回复了哪条评论)
    private Long rootId;   // 根评论
    private String targetUserName;  // 被回复人的名字

}
```

- 2.前端展示的评论所需要的字段
```java
@Data
public class CommentVO {
    private Long id;
    private Long articleId;
    private String userName;
    private String content;
    private String createTime;

    private Long parentId;
    private Long rootId;
    private String targetUserName;

    // 子评论，但是子评论不再有子评论，会根据 parentId 指定 父评论id
    private List<CommentVO> children = new ArrayList<>();
}
```