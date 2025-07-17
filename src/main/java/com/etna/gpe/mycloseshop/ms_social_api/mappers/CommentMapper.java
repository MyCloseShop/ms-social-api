package com.etna.gpe.mycloseshop.ms_social_api.mappers;

import com.etna.gpe.mycloseshop.ms_social_api.dtos.comment.CommentDto;
import com.etna.gpe.mycloseshop.ms_social_api.dtos.comment.CreateCommentDto;
import com.etna.gpe.mycloseshop.ms_social_api.entity.Comment;

import java.util.UUID;

public class CommentMapper {

    private CommentMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Comment requesToEntity(CreateCommentDto request) {
        if (request == null) return null;
        Comment comment = new Comment();
        comment.setContent(request.content());
        comment.setPostId(UUID.fromString(request.postId()));
        comment.setUserId(UUID.fromString(request.userId()));
        return comment;
    }

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
