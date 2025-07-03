package com.etna.gpe.mycloseshop.ms_social_api.services.comment;

import com.etna.gpe.mycloseshop.ms_social_api.dtos.comment.CommentDto;
import java.util.List;
import java.util.UUID;

public interface ICommentService {
    CommentDto createComment(CommentDto comment);
    List<CommentDto> getAllComments();
    CommentDto getCommentById(UUID id);
    CommentDto updateComment(UUID id, CommentDto comment);
    void deleteComment(UUID id);
    List<CommentDto> getCommentsByPost(UUID postId);
}
