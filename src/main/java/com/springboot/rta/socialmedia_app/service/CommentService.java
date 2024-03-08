package com.springboot.rta.socialmedia_app.service;

import com.github.fge.jsonpatch.JsonPatch;
import com.springboot.rta.socialmedia_app.dto.CommentDto;
import com.springboot.rta.socialmedia_app.dto.PatchDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getAllCommentsByPostId(long postId);
    CommentDto getCommentByPostIdAndCommentId(long postId,long id);

    CommentDto updateCommentsByPostIdAndCommentId(Long postId,long id, CommentDto commentDto);
    CommentDto updateCommentPartiallyByPostIdAndCommentId(long postId, long id, PatchDto patchDto);



    CommentDto updateCommentPartiallyByPostIdAndCommentIdUsingJsonPatch(Long postId, Long id, JsonPatch jsonPatch);
}
