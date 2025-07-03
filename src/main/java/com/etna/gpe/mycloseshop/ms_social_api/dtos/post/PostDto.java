package com.etna.gpe.mycloseshop.ms_social_api.dtos.post;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PostDto {
    private UUID postId;
    private String nom;
    private String description;
    private String media;
    private UUID shopId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
