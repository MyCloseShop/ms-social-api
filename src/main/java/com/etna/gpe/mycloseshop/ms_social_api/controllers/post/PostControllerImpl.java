package com.etna.gpe.mycloseshop.ms_social_api.controllers.post;

import com.etna.gpe.mycloseshop.ms_social_api.dtos.post.CreatePostDto;
import com.etna.gpe.mycloseshop.ms_social_api.dtos.post.PostDto;
import com.etna.gpe.mycloseshop.ms_social_api.services.post.IPostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
@SecurityRequirement(name = "bearerAuth")
public class PostControllerImpl implements IPostController {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    private final IPostService postService;

    public PostControllerImpl(IPostService postService) {
        this.postService = postService;
    }


    @Override
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody CreatePostDto post) {
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