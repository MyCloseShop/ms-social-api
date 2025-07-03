package com.etna.gpe.mycloseshop.ms_social_api.controllers.comment;

import com.etna.gpe.mycloseshop.ms_social_api.dtos.comment.CommentDto;
import com.etna.gpe.mycloseshop.ms_social_api.services.comment.ICommentService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.UUID;

@RestController
public class CommentControllerImpl implements ICommentController {

    @Autowired
    private ICommentService commentService;

    @PostMapping
    @Override
    public CommentDto createComment(@RequestBody CommentDto comment) {
        return commentService.createComment(comment);
    }

    @GetMapping
    @Override
    public List<CommentDto> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    @Override
    public CommentDto getCommentById(@PathVariable UUID id) {
        return commentService.getCommentById(id);
    }

    @PutMapping("/{id}")
    @Override
    public CommentDto updateComment(@PathVariable UUID id, @RequestBody CommentDto comment) {
        return commentService.updateComment(id, comment);
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteComment(@PathVariable UUID id) {
        commentService.deleteComment(id);
    }

    @GetMapping("/post/{postId}")
    @Override
    public List<CommentDto> getCommentsByPost(@PathVariable UUID postId) {
        return commentService.getCommentsByPost(postId);
    }
}
