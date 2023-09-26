package com.myblog5.myblog5.repository;

import com.myblog5.myblog5.entity.Comment;
import com.myblog5.myblog5.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRespository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(long postId);
}
