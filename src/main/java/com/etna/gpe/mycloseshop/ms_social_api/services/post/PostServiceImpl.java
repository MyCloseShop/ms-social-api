package com.etna.gpe.mycloseshop.ms_social_api.services.post;

import com.etna.gpe.mycloseshop.ms_social_api.dtos.post.PostDto;
import com.etna.gpe.mycloseshop.ms_social_api.exceptions.ResourceNotFoundException;
import com.etna.gpe.mycloseshop.ms_social_api.repository.PostRepository;
import com.etna.gpe.mycloseshop.ms_social_api.entity.Post;
import com.etna.gpe.mycloseshop.ms_social_api.mappers.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = PostMapper.toEntity(postDto);
        post.setId(UUID.randomUUID());
        Post saved = postRepository.save(post);
        return PostMapper.toDto(saved);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
            .map(PostMapper::toDto)
            .toList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public PostDto getPostById(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return PostMapper.toDto(post);
    }
    
    @Override
    public PostDto updatePost(UUID id, PostDto postDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        post.setNom(postDetails.getNom());
        post.setDescription(postDetails.getDescription());
        post.setMedia(postDetails.getMedia());
        post.setShopId(postDetails.getShopId());
        post.setUpdatedAt(LocalDateTime.now());
        Post updated = postRepository.save(post);
        return PostMapper.toDto(updated);
    }
    
    @Override
    public void deletePost(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        postRepository.delete(post);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PostDto> getPostsByShop(UUID shopId) {
        return postRepository.findByShopId(shopId).stream()
            .map(PostMapper::toDto)
            .toList();
    }
}
