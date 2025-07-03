package com.etna.gpe.mycloseshop.ms_social_api.repository;

import com.etna.gpe.mycloseshop.ms_social_api.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByShopId(UUID shopId);
}
