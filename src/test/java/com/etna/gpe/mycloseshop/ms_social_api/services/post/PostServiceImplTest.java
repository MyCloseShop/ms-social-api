package com.etna.gpe.mycloseshop.ms_social_api.services.post;

import com.etna.gpe.mycloseshop.ms_social_api.dtos.post.CreatePostDto;
import com.etna.gpe.mycloseshop.ms_social_api.dtos.post.PostDto;
import com.etna.gpe.mycloseshop.ms_social_api.entity.Post;
import com.etna.gpe.mycloseshop.ms_social_api.exceptions.ResourceNotFoundException;
import com.etna.gpe.mycloseshop.ms_social_api.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private Post post;
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

        post = new Post();
        post.setId(postId);
        post.setNom("Test Post");
        post.setDescription("This is a test post description");
        post.setMedia("http://example.com/media.jpg");
        post.setShopId(shopId);
        post.setCreatedAt(now);
        post.setUpdatedAt(now);

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
    void createPost_ShouldCreateAndReturnPost() {
        // Given
        when(postRepository.save(any(Post.class))).thenReturn(post);

        // When
        PostDto result = postService.createPost(createPostDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getPostId()).isEqualTo(postId);
        assertThat(result.getNom()).isEqualTo("Test Post");
        assertThat(result.getDescription()).isEqualTo("This is a test post description");
        assertThat(result.getMedia()).isEqualTo("http://example.com/media.jpg");
        assertThat(result.getShopId()).isEqualTo(shopId);

        verify(postRepository).save(any(Post.class));
    }

    @Test
    void getAllPosts_ShouldReturnListOfPosts() {
        // Given
        Post post2 = new Post();
        post2.setId(UUID.randomUUID());
        post2.setNom("Second Post");
        post2.setDescription("Second post description");
        post2.setMedia("http://example.com/media2.jpg");
        post2.setShopId(shopId);
        post2.setCreatedAt(now);
        post2.setUpdatedAt(now);

        List<Post> posts = Arrays.asList(post, post2);
        when(postRepository.findAll()).thenReturn(posts);

        // When
        List<PostDto> result = postService.getAllPosts();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNom()).isEqualTo("Test Post");
        assertThat(result.get(1).getNom()).isEqualTo("Second Post");

        verify(postRepository).findAll();
    }

    @Test
    void getPostById_WhenPostExists_ShouldReturnPost() {
        // Given
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // When
        PostDto result = postService.getPostById(postId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getPostId()).isEqualTo(postId);
        assertThat(result.getNom()).isEqualTo("Test Post");
        assertThat(result.getDescription()).isEqualTo("This is a test post description");

        verify(postRepository).findById(postId);
    }

    @Test
    void getPostById_WhenPostNotExists_ShouldThrowException() {
        // Given
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> postService.getPostById(postId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Post not found with id: " + postId);

        verify(postRepository).findById(postId);
    }

    @Test
    void updatePost_WhenPostExists_ShouldUpdateAndReturnPost() {
        // Given
        PostDto updateDto = PostDto.builder()
                .postId(postId)
                .nom("Updated Post")
                .description("Updated description")
                .media("http://example.com/updated-media.jpg")
                .shopId(shopId)
                .build();

        Post updatedPost = new Post();
        updatedPost.setId(postId);
        updatedPost.setNom("Updated Post");
        updatedPost.setDescription("Updated description");
        updatedPost.setMedia("http://example.com/updated-media.jpg");
        updatedPost.setShopId(shopId);
        updatedPost.setUpdatedAt(LocalDateTime.now());

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        // When
        PostDto result = postService.updatePost(postId, updateDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Updated Post");
        assertThat(result.getDescription()).isEqualTo("Updated description");
        assertThat(result.getMedia()).isEqualTo("http://example.com/updated-media.jpg");

        verify(postRepository).findById(postId);
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void updatePost_WhenPostNotExists_ShouldThrowException() {
        // Given
        PostDto updateDto = PostDto.builder()
                .postId(postId)
                .nom("Updated Post")
                .description("Updated description")
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> postService.updatePost(postId, updateDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Post not found with id: " + postId);

        verify(postRepository).findById(postId);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void deletePost_WhenPostExists_ShouldDeletePost() {
        // Given
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        doNothing().when(postRepository).delete(post);

        // When
        postService.deletePost(postId);

        // Then
        verify(postRepository).findById(postId);
        verify(postRepository).delete(post);
    }

    @Test
    void deletePost_WhenPostNotExists_ShouldThrowException() {
        // Given
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> postService.deletePost(postId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Post not found with id: " + postId);

        verify(postRepository).findById(postId);
        verify(postRepository, never()).delete(any(Post.class));
    }

    @Test
    void getPostsByShop_ShouldReturnPostsForShop() {
        // Given
        List<Post> shopPosts = List.of(post);
        when(postRepository.findByShopId(shopId)).thenReturn(shopPosts);

        // When
        List<PostDto> result = postService.getPostsByShop(shopId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPostId()).isEqualTo(postId);
        assertThat(result.get(0).getShopId()).isEqualTo(shopId);
        assertThat(result.get(0).getNom()).isEqualTo("Test Post");

        verify(postRepository).findByShopId(shopId);
    }

    @Test
    void getPostsByShop_WhenNoPostsExist_ShouldReturnEmptyList() {
        // Given
        when(postRepository.findByShopId(shopId)).thenReturn(List.of());

        // When
        List<PostDto> result = postService.getPostsByShop(shopId);

        // Then
        assertThat(result).isEmpty();

        verify(postRepository).findByShopId(shopId);
    }
}
