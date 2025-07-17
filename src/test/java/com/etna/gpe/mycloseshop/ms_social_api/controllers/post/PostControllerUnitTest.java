package com.etna.gpe.mycloseshop.ms_social_api.controllers.post;

import com.etna.gpe.mycloseshop.ms_social_api.dtos.post.CreatePostDto;
import com.etna.gpe.mycloseshop.ms_social_api.dtos.post.PostDto;
import com.etna.gpe.mycloseshop.ms_social_api.services.post.IPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostControllerUnitTest {

    @Mock
    private IPostService postService;

    @InjectMocks
    private PostControllerImpl postController;

    private PostDto postDto;
    private CreatePostDto createPostDto;
    private UUID postId;
    private UUID shopId;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        postId = UUID.randomUUID();
        shopId = UUID.randomUUID();
        now = LocalDateTime.now();

        createPostDto = new CreatePostDto(
                "Test Post",
                "This is a test post description",
                "http://example.com/media.jpg",
                shopId.toString()
        );

        postDto = PostDto.builder()
                .postId(postId)
                .nom("Test Post")
                .description("This is a test post description")
                .media("http://example.com/media.jpg")
                .shopId(shopId)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    @Test
    void createPost_ShouldReturnCreatedPost() {
        // Given
        when(postService.createPost(any(CreatePostDto.class))).thenReturn(postDto);

        // When
        ResponseEntity<PostDto> result = postController.createPost(createPostDto);

        // Then
        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getPostId()).isEqualTo(postId);
        assertThat(result.getBody().getNom()).isEqualTo("Test Post");
        assertThat(result.getBody().getDescription()).isEqualTo("This is a test post description");
        assertThat(result.getBody().getShopId()).isEqualTo(shopId);

        verify(postService).createPost(createPostDto);
    }

    @Test
    void getAllPosts_ShouldReturnListOfPosts() {
        // Given
        PostDto post2 = PostDto.builder()
                .postId(UUID.randomUUID())
                .nom("Second Post")
                .description("Second post description")
                .media("http://example.com/media2.jpg")
                .shopId(shopId)
                .createdAt(now)
                .updatedAt(now)
                .build();

        List<PostDto> posts = Arrays.asList(postDto, post2);
        when(postService.getAllPosts()).thenReturn(posts);

        // When
        ResponseEntity<List<PostDto>> result = postController.getAllPosts();

        // Then
        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getBody()).hasSize(2);
        assertThat(result.getBody().get(0).getNom()).isEqualTo("Test Post");
        assertThat(result.getBody().get(1).getNom()).isEqualTo("Second Post");

        verify(postService).getAllPosts();
    }

    @Test
    void getPostById_ShouldReturnPost() {
        // Given
        when(postService.getPostById(postId)).thenReturn(postDto);

        // When
        ResponseEntity<PostDto> result = postController.getPostById(postId);

        // Then
        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getPostId()).isEqualTo(postId);
        assertThat(result.getBody().getNom()).isEqualTo("Test Post");

        verify(postService).getPostById(postId);
    }

    @Test
    void updatePost_ShouldReturnUpdatedPost() {
        // Given
        PostDto updatedPost = PostDto.builder()
                .postId(postId)
                .nom("Updated Post")
                .description("Updated description")
                .media("http://example.com/updated-media.jpg")
                .shopId(shopId)
                .createdAt(now)
                .updatedAt(now)
                .build();

        when(postService.updatePost(eq(postId), any(PostDto.class)))
                .thenReturn(updatedPost);

        // When
        ResponseEntity<PostDto> result = postController.updatePost(postId, updatedPost);

        // Then
        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getNom()).isEqualTo("Updated Post");
        assertThat(result.getBody().getDescription()).isEqualTo("Updated description");

        verify(postService).updatePost(postId, updatedPost);
    }

    @Test
    void deletePost_ShouldReturnNoContent() {
        // Given
        doNothing().when(postService).deletePost(postId);

        // When
        ResponseEntity<Void> result = postController.deletePost(postId);

        // Then
        assertThat(result.getStatusCode().value()).isEqualTo(204);
        assertThat(result.getBody()).isNull();

        verify(postService).deletePost(postId);
    }

    @Test
    void getPostsByShop_ShouldReturnPostsForShop() {
        // Given
        List<PostDto> shopPosts = List.of(postDto);
        when(postService.getPostsByShop(shopId)).thenReturn(shopPosts);

        // When
        ResponseEntity<List<PostDto>> result = postController.getPostsByShop(shopId);

        // Then
        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody().get(0).getPostId()).isEqualTo(postId);
        assertThat(result.getBody().get(0).getShopId()).isEqualTo(shopId);

        verify(postService).getPostsByShop(shopId);
    }
}
