package com.etna.gpe.mycloseshop.ms_social_api.mappers;

import com.etna.gpe.mycloseshop.ms_social_api.dtos.post.CreatePostDto;
import com.etna.gpe.mycloseshop.ms_social_api.dtos.post.PostDto;
import com.etna.gpe.mycloseshop.ms_social_api.entity.Post;

import java.util.UUID;

public class PostMapper {

    private PostMapper() {
        // Private constructor to prevent instantiation
    }

    public static Post requestToEntity(CreatePostDto post) {
        if (post == null) return null;
        Post entity = new Post();
        entity.setNom(post.nom());
        entity.setDescription(post.description());
        entity.setMedia(post.media());
        entity.setShopId(UUID.fromString(post.shopId()));
        return entity;
    }

    public static PostDto toDto(Post post) {
        if (post == null) return null;
        return PostDto.builder()
                .postId(post.getId())
                .nom(post.getNom())
                .description(post.getDescription())
                .media(post.getMedia())
                .shopId(post.getShopId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public static Post toEntity(PostDto dto) {
        if (dto == null) return null;
        Post post = new Post();
        post.setId(dto.getPostId());
        post.setNom(dto.getNom());
        post.setDescription(dto.getDescription());
        post.setMedia(dto.getMedia());
        post.setShopId(dto.getShopId());
        post.setCreatedAt(dto.getCreatedAt());
        post.setUpdatedAt(dto.getUpdatedAt());
        return post;
    }
}
