package com.etna.gpe.mycloseshop.ms_social_api.controllers.post;

import com.etna.gpe.mycloseshop.ms_social_api.dtos.post.PostDto;
import com.etna.gpe.mycloseshop.ms_social_api.services.post.IPostService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class PostControllerImpl implements IPostController {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    public PostControllerImpl(IPostService postService) {
        this.postService = postService;
    }

    @Autowired
    private final IPostService postService;

    @Override
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto post) {
        return ResponseEntity.ok(postService.createPost(post));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable UUID id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @Override
    @PutMapping("/{id}")
    // @TODO: checker si le user est le owner du shop
    public ResponseEntity<PostDto> updatePost(@PathVariable UUID id, @RequestBody PostDto post) {
        return ResponseEntity.ok(postService.updatePost(id, post));
    }

    @Override
    @DeleteMapping("/{id}")
    // @TODO: checker si le user est le owner du shop
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/shop/{shopId}")
    public ResponseEntity<List<PostDto>> getPostsByShop(@PathVariable UUID shopId) {
        return ResponseEntity.ok(postService.getPostsByShop(shopId));
    }
}