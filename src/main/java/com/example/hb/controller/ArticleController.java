package com.example.hb.controller;

import com.example.hb.common.Result;
import com.example.hb.service.CommentService;
import com.example.hb.vo.CommentVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ArticleController {
    private final CommentService commentService;

    public ArticleController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 获取当前游客身份（没有就生成一个）
    @GetMapping("/user/current")
    public Result<String> currentUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            username = "游客_" + UUID.randomUUID().toString().substring(0, 6);
            session.setAttribute("username", username);
        }
        return Result.success(username);
    }

    // 获取某篇文章的两级评论结构
    @GetMapping("/comment/list/{articleId}")
    public Result<List<CommentVO>> list(@PathVariable Long articleId) {
        return Result.success(commentService.getCommentsForArticle(articleId));
    }
}
