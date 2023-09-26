package com.myblog5.myblog5.service.impl;

import com.myblog5.myblog5.entity.Comment;
import com.myblog5.myblog5.entity.Post;
import com.myblog5.myblog5.exception.ResourceNotFound;
import com.myblog5.myblog5.payload.CommentDto;
import com.myblog5.myblog5.repository.CommentRespository;
import com.myblog5.myblog5.repository.PostRepository;
import com.myblog5.myblog5.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRespository commentRespository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRespository commentRespository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRespository = commentRespository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id: " + postId)
        );
        comment.setPost(post);
        Comment savedComment = commentRespository.save(comment);

        CommentDto dto = mapToDto(savedComment);
        return null;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFound("Post not found with id:" +postId)
        );
        List<Comment> comments = commentRespository.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());


        return commentDtos;
    }

    @Override
    public CommentDto getCommentsById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id: " + postId)
        );
        Comment comment = commentRespository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("Comment not found with id: " + commentId));
        CommentDto commentDto = mapToDto(comment);
        return commentDto;
    }

    @Override
    public List<CommentDto> getAllCommentsById() {
        List<Comment> comments = commentRespository.findAll();
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id: " + postId)
        );
        Comment comment = commentRespository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("Comment not found with id: " + commentId));
        commentRespository.deleteById(commentId);

    }


    private CommentDto mapToDto(Comment savedComment) {
        CommentDto dto = mapper.map(savedComment, CommentDto.class);
        return dto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        return comment;
    }
}
