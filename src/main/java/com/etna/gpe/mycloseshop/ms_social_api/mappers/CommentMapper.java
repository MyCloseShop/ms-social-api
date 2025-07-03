package com.etna.gpe.mycloseshop.ms_social_api.mappers;

import com.etna.gpe.mycloseshop.ms_social_api.entity.Comment;
import com.etna.gpe.mycloseshop.ms_social_api.dtos.comment.CommentDto;

public class CommentMapper {
    public static CommentDto toDto(Comment comment) {
        if (comment == null) return null;
        return CommentDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .postId(comment.getPostId())
                .userId(comment.getUserId())
                .build();
    }

    public static Comment toEntity(CommentDto dto) {
        if (dto == null) return null;
        Comment comment = new Comment();
        comment.setId(dto.getCommentId());
        comment.setContent(dto.getContent());
        comment.setPostId(dto.getPostId());
        comment.setUserId(dto.getUserId());
        return comment;
    }
}
