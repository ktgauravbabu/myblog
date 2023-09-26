package com.myblog5.myblog5.repository;

import com.myblog5.myblog5.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
