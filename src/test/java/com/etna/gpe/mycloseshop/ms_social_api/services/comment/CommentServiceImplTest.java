package com.etna.gpe.mycloseshop.ms_social_api.services.comment;

import com.etna.gpe.mycloseshop.ms_social_api.dtos.comment.CommentDto;
import com.etna.gpe.mycloseshop.ms_social_api.dtos.comment.CreateCommentDto;
import com.etna.gpe.mycloseshop.ms_social_api.entity.Comment;
import com.etna.gpe.mycloseshop.ms_social_api.exceptions.ResourceNotFoundException;
import com.etna.gpe.mycloseshop.ms_social_api.repository.CommentRepository;
import com.etna.gpe.mycloseshop.security_api.entity.JwtUserDetails;
import com.etna.gpe.mycloseshop.security_api.utils.security.ISecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ISecurityUtils securityUtils;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private JwtUserDetails jwtUserDetails;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Comment comment;
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

        comment = new Comment();
        comment.setId(commentId);
        comment.setContent("This is a test comment");
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setCreatedAt(LocalDateTime.now());

        commentDto = CommentDto.builder()
                .commentId(commentId)
                .content("This is a test comment")
                .postId(postId)
                .userId(userId)
                .build();

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createComment_ShouldCreateAndReturnComment() {
        // Given
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(jwtUserDetails);
        when(jwtUserDetails.getUserId()).thenReturn(userId.toString());
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // When
        CommentDto result = commentService.createComment(createCommentDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCommentId()).isEqualTo(commentId);
        assertThat(result.getContent()).isEqualTo("This is a test comment");
        assertThat(result.getPostId()).isEqualTo(postId);
        assertThat(result.getUserId()).isEqualTo(userId);

        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void getAllComments_ShouldReturnListOfComments() {
        // Given
        Comment comment2 = new Comment();
        comment2.setId(UUID.randomUUID());
        comment2.setContent("Second comment");
        comment2.setPostId(postId);
        comment2.setUserId(userId);

        List<Comment> comments = Arrays.asList(comment, comment2);
        when(commentRepository.findAll()).thenReturn(comments);

        // When
        List<CommentDto> result = commentService.getAllComments();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getContent()).isEqualTo("This is a test comment");
        assertThat(result.get(1).getContent()).isEqualTo("Second comment");

        verify(commentRepository).findAll();
    }

    @Test
    void getCommentById_WhenCommentExists_ShouldReturnComment() {
        // Given
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // When
        CommentDto result = commentService.getCommentById(commentId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCommentId()).isEqualTo(commentId);
        assertThat(result.getContent()).isEqualTo("This is a test comment");

        verify(commentRepository).findById(commentId);
    }

    @Test
    void getCommentById_WhenCommentNotExists_ShouldThrowException() {
        // Given
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> commentService.getCommentById(commentId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Comment not found with id: " + commentId);

        verify(commentRepository).findById(commentId);
    }

    @Test
    void updateComment_WhenUserIsOwner_ShouldUpdateAndReturnComment() {
        // Given
        CommentDto updateDto = CommentDto.builder()
                .commentId(commentId)
                .content("Updated content")
                .postId(postId)
                .userId(userId)
                .build();

        Comment updatedComment = new Comment();
        updatedComment.setId(commentId);
        updatedComment.setContent("Updated content");
        updatedComment.setPostId(postId);
        updatedComment.setUserId(userId);
        updatedComment.setUpdatedAt(LocalDateTime.now());

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        doNothing().when(securityUtils).checkUserIsOwner(userId);
        when(commentRepository.save(any(Comment.class))).thenReturn(updatedComment);

        // When
        CommentDto result = commentService.updateComment(commentId, updateDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo("Updated content");

        verify(commentRepository).findById(commentId);
        verify(securityUtils).checkUserIsOwner(userId);
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void updateComment_WhenUserIsNotOwner_ShouldThrowException() {
        // Given
        CommentDto updateDto = CommentDto.builder()
                .commentId(commentId)
                .content("Updated content")
                .postId(postId)
                .userId(userId)
                .build();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        doThrow(new SecurityException("User is not owner")).when(securityUtils).checkUserIsOwner(userId);

        // When & Then
        assertThatThrownBy(() -> commentService.updateComment(commentId, updateDto))
                .isInstanceOf(ResponseStatusException.class);

        verify(commentRepository).findById(commentId);
        verify(securityUtils).checkUserIsOwner(userId);
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void deleteComment_WhenUserIsOwner_ShouldDeleteComment() {
        // Given
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        doNothing().when(securityUtils).checkUserIsOwner(userId);
        doNothing().when(commentRepository).delete(comment);

        // When
        commentService.deleteComment(commentId);

        // Then
        verify(commentRepository).findById(commentId);
        verify(securityUtils).checkUserIsOwner(userId);
        verify(commentRepository).delete(comment);
    }

    @Test
    void deleteComment_WhenUserIsNotOwner_ShouldThrowException() {
        // Given
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        doThrow(new SecurityException("User is not owner")).when(securityUtils).checkUserIsOwner(userId);

        // When & Then
        assertThatThrownBy(() -> commentService.deleteComment(commentId))
                .isInstanceOf(ResponseStatusException.class);

        verify(commentRepository).findById(commentId);
        verify(securityUtils).checkUserIsOwner(userId);
        verify(commentRepository, never()).delete(any(Comment.class));
    }

    @Test
    void getCommentsByPost_ShouldReturnCommentsForPost() {
        // Given
        List<Comment> postComments = Arrays.asList(comment);
        when(commentRepository.findByPostId(postId)).thenReturn(postComments);

        // When
        List<CommentDto> result = commentService.getCommentsByPost(postId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCommentId()).isEqualTo(commentId);
        assertThat(result.get(0).getPostId()).isEqualTo(postId);

        verify(commentRepository).findByPostId(postId);
    }
}
