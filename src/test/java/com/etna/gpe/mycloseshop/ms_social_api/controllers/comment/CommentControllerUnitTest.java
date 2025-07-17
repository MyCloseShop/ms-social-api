package com.etna.gpe.mycloseshop.ms_social_api.controllers.comment;

import com.etna.gpe.mycloseshop.ms_social_api.dtos.comment.CommentDto;
import com.etna.gpe.mycloseshop.ms_social_api.dtos.comment.CreateCommentDto;
import com.etna.gpe.mycloseshop.ms_social_api.services.comment.ICommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentControllerUnitTest {

    @Mock
    private ICommentService commentService;

    @InjectMocks
    private CommentControllerImpl commentController;

    private CommentDto commentDto;
    private CreateCommentDto createCommentDto;
    private UUID commentId;
    private UUID postId;
    private UUID userId;

    @BeforeEach
    void setUp() {
        commentId = UUID.randomUUID();
        postId = UUID.randomUUID();
        userId = UUID.randomUUID();

        createCommentDto = new CreateCommentDto(
                "This is a test comment",
                postId.toString(),
                userId.toString()
        );

        commentDto = CommentDto.builder()
                .commentId(commentId)
                .content("This is a test comment")
                .postId(postId)
                .userId(userId)
                .build();
    }

    @Test
    void createComment_ShouldReturnCreatedComment() {
        // Given
        when(commentService.createComment(any(CreateCommentDto.class))).thenReturn(commentDto);

        // When
        CommentDto result = commentController.createComment(createCommentDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCommentId()).isEqualTo(commentId);
        assertThat(result.getContent()).isEqualTo("This is a test comment");
        assertThat(result.getPostId()).isEqualTo(postId);
        assertThat(result.getUserId()).isEqualTo(userId);

        verify(commentService).createComment(createCommentDto);
    }

    @Test
    void getAllComments_ShouldReturnListOfComments() {
        // Given
        CommentDto comment2 = CommentDto.builder()
                .commentId(UUID.randomUUID())
                .content("Second comment")
                .postId(postId)
                .userId(userId)
                .build();

        List<CommentDto> comments = Arrays.asList(commentDto, comment2);
        when(commentService.getAllComments()).thenReturn(comments);

        // When
        List<CommentDto> result = commentController.getAllComments();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getContent()).isEqualTo("This is a test comment");
        assertThat(result.get(1).getContent()).isEqualTo("Second comment");

        verify(commentService).getAllComments();
    }

    @Test
    void getCommentById_ShouldReturnComment() {
        // Given
        when(commentService.getCommentById(commentId)).thenReturn(commentDto);

        // When
        CommentDto result = commentController.getCommentById(commentId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCommentId()).isEqualTo(commentId);
        assertThat(result.getContent()).isEqualTo("This is a test comment");

        verify(commentService).getCommentById(commentId);
    }

    @Test
    void updateComment_ShouldReturnUpdatedComment() {
        // Given
        CommentDto updatedComment = CommentDto.builder()
                .commentId(commentId)
                .content("Updated comment content")
                .postId(postId)
                .userId(userId)
                .build();

        when(commentService.updateComment(eq(commentId), any(CommentDto.class)))
                .thenReturn(updatedComment);

        // When
        CommentDto result = commentController.updateComment(commentId, updatedComment);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo("Updated comment content");

        verify(commentService).updateComment(commentId, updatedComment);
    }

    @Test
    void deleteComment_ShouldCallServiceDelete() {
        // Given
        doNothing().when(commentService).deleteComment(commentId);

        // When
        commentController.deleteComment(commentId);

        // Then
        verify(commentService).deleteComment(commentId);
    }

    @Test
    void getCommentsByPost_ShouldReturnCommentsForPost() {
        // Given
        List<CommentDto> postComments = List.of(commentDto);
        when(commentService.getCommentsByPost(postId)).thenReturn(postComments);

        // When
        List<CommentDto> result = commentController.getCommentsByPost(postId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCommentId()).isEqualTo(commentId);
        assertThat(result.get(0).getPostId()).isEqualTo(postId);

        verify(commentService).getCommentsByPost(postId);
    }
}
