package com.etna.gpe.mycloseshop.ms_social_api.dtos.post;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

public record CreatePostDto(
        @Length(min = 1, max = 36)
        @NotBlank
        String nom,
        String description,
        String media,
        @NotBlank
        @UUID
        String shopId
) {
}
