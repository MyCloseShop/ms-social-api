package com.etna.gpe.mycloseshop.ms_social_api.services.post;

import com.etna.gpe.mycloseshop.ms_social_api.dtos.post.CreatePostDto;
import com.etna.gpe.mycloseshop.ms_social_api.dtos.post.PostDto;

import java.util.List;
import java.util.UUID;

public interface IPostService {
    PostDto createPost(CreatePostDto post);
    List<PostDto> getAllPosts();
    PostDto getPostById(UUID id);
    PostDto updatePost(UUID id, PostDto post);
    void deletePost(UUID id);
    List<PostDto> getPostsByShop(UUID shopId);
}
