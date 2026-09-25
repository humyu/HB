package com.example.hb.service;

import com.example.hb.vo.CommentVO;
import com.example.hb.entity.Comment;
import com.example.hb.mapper.CommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {

    private final CommentMapper commentMapper;

    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    /**
     * 获取所有评论
     * @param articleId 文章id
     * @return 评论列表
     */
    public List<CommentVO> getCommentsForArticle(Long articleId) {
        List<Comment> allComments =
                commentMapper.findByArticleIdOrderByCreateTimeAsc(articleId);

        List<CommentVO> rootComments = new ArrayList<>();
        Map<Long, CommentVO> rootMap = new HashMap<>();

        // 第一遍：找一级评论
        for (Comment c : allComments) {
            if (c.getRootId() == null) {
                CommentVO vo = convertToVO(c);
                rootComments.add(vo);
                rootMap.put(c.getId(), vo);
            }
        }

        // 第二遍：把所有子评论挂到对应一级评论下
        for (Comment c : allComments) {
            if (c.getRootId() != null) {
                CommentVO rootVO = rootMap.get(c.getRootId());
                if (rootVO != null) {
                    rootVO.getChildren().add(convertToVO(c));
                }
            }
        }

        return rootComments;
    }

    @Transactional
    public CommentVO addComment(Long articleId,
                                String userName,
                                String content,
                                Long parentId,
                                Long rootId,
                                String targetUserName) {

        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setUserName(userName);
        comment.setContent(content);
        comment.setParentId(parentId);
        comment.setCreateTime(LocalDateTime.now());

        if (parentId == null) {
            // 一级评论
            comment.setRootId(null);
            comment.setTargetUserName(null);
        } else {
            // 不信任前端传来的 rootId/targetUserName，根据 parentId 重新推导
            Comment parent = commentMapper.findById(parentId);
            if (parent == null) {
                throw new IllegalArgumentException("被回复的评论不存在");
            }

            if (parent.getRootId() == null) {
                // 回复的是一级评论
                comment.setRootId(parent.getId());
            } else {
                // 回复的是二级或更深，rootId 继续沿用
                comment.setRootId(parent.getRootId());
            }

            comment.setTargetUserName(parent.getUserName());
        }

        commentMapper.insert(comment);
        return convertToVO(comment);
    }

    private CommentVO convertToVO(Comment c) {
        CommentVO vo = new CommentVO();
        vo.setId(c.getId());
        vo.setArticleId(c.getArticleId());
        vo.setParentId(c.getParentId());
        vo.setRootId(c.getRootId());
        vo.setUserName(c.getUserName());
        vo.setContent(c.getContent());
        vo.setTargetUserName(c.getTargetUserName());

        if (c.getCreateTime() != null) {
            vo.setCreateTime(c.getCreateTime()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } else {
            vo.setCreateTime("");
        }

        return vo;
    }
}
