package com.etna.gpe.mycloseshop.ms_social_api.dtos.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public record CreateCommentDto(
        @NotBlank
        String content,
        @NotNull
        @UUID
        String postId,
        @NotNull
        @UUID
        String userId
) {
}
