package com.etna.gpe.mycloseshop.ms_social_api.dtos.comment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CommentDto {
    private UUID commentId;
    private String content;
    private UUID postId;
    private UUID userId;
}
