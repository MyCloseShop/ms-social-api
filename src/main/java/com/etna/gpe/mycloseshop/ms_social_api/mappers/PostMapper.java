package com.etna.gpe.mycloseshop.ms_social_api.mappers;

import com.etna.gpe.mycloseshop.ms_social_api.entity.Post;
import com.etna.gpe.mycloseshop.ms_social_api.dtos.post.PostDto;

public class PostMapper {
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
