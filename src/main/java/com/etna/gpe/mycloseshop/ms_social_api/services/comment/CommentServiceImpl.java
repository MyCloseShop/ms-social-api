package com.etna.gpe.mycloseshop.ms_social_api.services.comment;

import com.etna.gpe.mycloseshop.ms_social_api.dtos.comment.CommentDto;
import com.etna.gpe.mycloseshop.ms_social_api.dtos.comment.CreateCommentDto;
import com.etna.gpe.mycloseshop.ms_social_api.entity.Comment;
import com.etna.gpe.mycloseshop.ms_social_api.exceptions.ResourceNotFoundException;
import com.etna.gpe.mycloseshop.ms_social_api.mappers.CommentMapper;
import com.etna.gpe.mycloseshop.ms_social_api.repository.CommentRepository;
import com.etna.gpe.mycloseshop.security_api.entity.JwtUserDetails;
import com.etna.gpe.mycloseshop.security_api.utils.security.ISecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CommentServiceImpl implements ICommentService {

    public static final String COMMENT_NOT_FOUND_WITH_ID = "Comment not found with id: ";
    private final CommentRepository commentRepository;
    private final ISecurityUtils securityUtils;

    public CommentServiceImpl(CommentRepository commentRepository, ISecurityUtils securityUtils) {
        this.commentRepository = commentRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public CommentDto createComment(CreateCommentDto commentDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();

        Comment comment = CommentMapper.requesToEntity(commentDto);
        comment.setUserId(UUID.fromString(jwtUserDetails.getUserId()));
        comment.setCreatedAt(LocalDateTime.now());

        Comment saved = commentRepository.save(comment);
        return CommentMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream()
            .map(CommentMapper::toDto)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getCommentById(UUID id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT_NOT_FOUND_WITH_ID + id));
        return CommentMapper.toDto(comment);
    }

    @Override
    public CommentDto updateComment(UUID id, CommentDto commentDetails) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT_NOT_FOUND_WITH_ID + id));
        try {
            securityUtils.checkUserIsOwner(comment.getUserId());
        } catch (SecurityException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }

        comment.setContent(commentDetails.getContent());
        comment.setUpdatedAt(LocalDateTime.now());
        Comment updated = commentRepository.save(comment);
        return CommentMapper.toDto(updated);
    }

    @Override
    public void deleteComment(UUID id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT_NOT_FOUND_WITH_ID + id));
        try {
            securityUtils.checkUserIsOwner(comment.getUserId());
        } catch (SecurityException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }

        commentRepository.delete(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByPost(UUID postId) {
        return commentRepository.findByPostId(postId).stream()
            .map(CommentMapper::toDto)
            .toList();
    }
}