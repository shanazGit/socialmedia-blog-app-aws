package com.springboot.rta.socialmedia_app.service;

import com.springboot.rta.socialmedia_app.dto.CommentDto;
import com.springboot.rta.socialmedia_app.dto.PatchDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getAllCommentsByPostId(long postId);
    CommentDto getCommentByPostIdAndCommentId(long postId,long id);

    CommentDto updateCommentsByPostIdAndCommentId(Long postId);
    CommentDto updateCommentPartiallyByPostIdAndCommentId(long postId, long id, PatchDto patchDto);
}
