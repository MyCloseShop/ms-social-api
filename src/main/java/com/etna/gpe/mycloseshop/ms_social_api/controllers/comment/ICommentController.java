package com.etna.gpe.mycloseshop.ms_social_api.controllers.comment;

import com.etna.gpe.mycloseshop.ms_social_api.dtos.comment.CommentDto;


import com.etna.gpe.mycloseshop.common_api.ms_login.dto.error.ResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Tag(name = "Comments Management", description = "API for managing comments")
@RequestMapping("/comments")
public interface ICommentController {
    @PostMapping
    @Operation(summary = "Create a comment")
    @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Comment created", content = @Content(schema = @Schema(implementation = CommentDto.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })  
    public CommentDto createComment(
            @Parameter(description = "Comment to create", required = true)
            @RequestBody CommentDto comment);
    
    @GetMapping
    @Operation(summary = "Get all comments")
    @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Comments found", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentDto.class)))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })
    public List<CommentDto> getAllComments();
    
    @GetMapping(path = "/{id}")
    @Operation(summary = "Get a comment by its ID")
    @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Comment found", content = @Content(schema = @Schema(implementation = CommentDto.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseError.class))),
                        @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })
    public CommentDto getCommentById(UUID id);
    
    @PutMapping(path = "/{id}")
    @Operation(summary = "Update a comment")
    @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Comment found", content = @Content(schema = @Schema(implementation = CommentDto.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseError.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseError.class))),
                        @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })
    public CommentDto updateComment(
            @Parameter(description = "id", required = true) 
            @PathVariable("id") UUID id,
            @Parameter(description = "Comment to update", required = true)
            @RequestBody CommentDto comment);
    
    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a comment")
    @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Comment found", content = @Content(schema = @Schema(implementation = CommentDto.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseError.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseError.class))),
                        @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })
    public void deleteComment(
            @Parameter(description = "id", required = true) 
            @PathVariable("id") UUID id);
    
    @GetMapping(path = "/post/{postId}")
    @Operation(summary = "Get comments by post")
    @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Comments found", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentDto.class)))),
                        @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(schema = @Schema(implementation = ResponseError.class))),

                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })
    public List<CommentDto> getCommentsByPost(
            @Parameter(description = "postId", required = true) 
            @PathVariable("postId") UUID postId);
}
