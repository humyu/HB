package com.example.hb.controller;


import com.example.hb.vo.CommentVO;
import com.example.hb.common.Result;
import com.example.hb.dto.CommentAddDTO;
import com.example.hb.service.CommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@RequestMapping("/api/comment")
public class CommentApiController {
    private CommentService commentService;

    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public Result<CommentVO> add(@RequestBody CommentAddDTO dto,
                                 HttpSession session) {

        String username = (String) session.getAttribute("username");
        if (username == null) {
            username = "游客_" + UUID.randomUUID().toString().substring(0, 6);
            session.setAttribute("username", username);
        }

        if (dto.getArticleId() == null) {
            return Result.fail("文章ID不能为空");
        }

        if (dto.getContent() == null || dto.getContent().trim().isEmpty()) {
            return Result.fail("评论内容不能为空");
        }

        try {
            CommentVO vo = commentService.addComment(
                    dto.getArticleId(),
                    username,
                    dto.getContent().trim(),
                    dto.getParentId(),
                    dto.getRootId(),
                    dto.getTargetUserName()
            );
            return Result.success(vo);
        } catch (IllegalArgumentException e) {
            return Result.fail(e.getMessage());
        }
    }
}
