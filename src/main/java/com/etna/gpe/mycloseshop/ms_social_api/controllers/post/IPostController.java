package com.etna.gpe.mycloseshop.ms_social_api.controllers.post;

import com.etna.gpe.mycloseshop.common_api.ms_login.dto.error.ResponseError;
import com.etna.gpe.mycloseshop.ms_social_api.dtos.post.CreatePostDto;
import com.etna.gpe.mycloseshop.ms_social_api.dtos.post.PostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Tag(name = "Posts Management", description = "API for managing posts")
@RequestMapping("/posts")
public interface IPostController {
        @PostMapping
        @Operation(summary = "Create a new post")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Post created", content = @Content(schema = @Schema(implementation = PostDto.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseError.class)))
        })
        ResponseEntity<PostDto> createPost(
                @Parameter(description = "Post to create", required = true)
                @RequestBody CreatePostDto post);

        @GetMapping
        @Operation(summary = "Get all posts")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Posts found", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PostDto.class)))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseError.class)))
        })
        ResponseEntity<List<PostDto>> getAllPosts();

        @GetMapping(path = "/{id}")
        @Operation(summary = "Get a post by its ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Post found", content = @Content(schema = @Schema(implementation = PostDto.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseError.class))),
                        @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(schema = @Schema(implementation = ResponseError.class)))
        })
        ResponseEntity<PostDto> getPostById(
                @Parameter(description = "id", required = true) 
                @PathVariable("id") UUID id);

        @PutMapping(path = "/{id}")
        @Operation(summary = "Update a post")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Post found", content = @Content(schema = @Schema(implementation = PostDto.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseError.class))),
                        @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(schema = @Schema(implementation = ResponseError.class)))
        })
        ResponseEntity<PostDto> updatePost(
                @Parameter(description = "id", required = true) 
                @PathVariable("id") UUID id,
                @Parameter(description = "Post to update", required = true)
                @RequestBody PostDto post);

        @DeleteMapping(path = "/{id}")
        @Operation(summary = "Delete a post")
        ResponseEntity<Void> deletePost(
                @Parameter(description = "id", required = true) 
                @PathVariable("id") UUID id);

        @Operation(summary = "Get all posts of a shop")
        ResponseEntity<List<PostDto>> getPostsByShop(
                @Parameter(description = "shopId", required = true) 
                @PathVariable("shopId") UUID shopId);
}