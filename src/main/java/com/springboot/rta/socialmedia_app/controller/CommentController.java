package com.springboot.rta.socialmedia_app.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.springboot.rta.socialmedia_app.dto.CommentDto;
import com.springboot.rta.socialmedia_app.dto.PatchDto;
import com.springboot.rta.socialmedia_app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CommentController {

   @Autowired
    private CommentService commentService;


    // Create new Post - /api/v1/posts/{postId}/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long postId, @RequestBody CommentDto commentDto) {
        CommentDto savedCommentDto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(savedCommentDto, HttpStatus.CREATED);
    }
    //Get All Comments -  /api/v1/posts/{postId}/comments
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> fetchAllCommentsByPostId(@PathVariable("postId") Long postId) {
        List<CommentDto> commentDtoList = commentService.getAllCommentsByPostId(postId);
        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }
    //Get All Comments -  /api/v1/posts/{postId}/comments/{commentId}
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<List<CommentDto>> fetchCommentsByPostIdAndCommentId(@PathVariable("postId") Long postId,@PathVariable("id")long id) {
        List<CommentDto> commentDto = commentService.getAllCommentsByPostId(postId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    //update Comments -  /api/v1/posts/{postId}/comments/{commentId}
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateCommentsByPostIdAndCommentId(@PathVariable("postId") Long postId,
                                                                               @PathVariable("id")long id,
                                                                               @RequestBody CommentDto commentDto) {
        CommentDto updatedCommentDto = commentService.updateCommentsByPostIdAndCommentId(postId,id,commentDto);
        return new ResponseEntity<>(updatedCommentDto, HttpStatus.OK);
    }

    //Patch Comment By CommentId and PostId
    // @PatchMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> partiallyUpdateCommentByPostIdAndCommentId(@PathVariable("postId") Long postId,
                                                                                 @PathVariable("id") Long id,
                                                                                 @RequestBody PatchDto patchDto) {
        CommentDto updatedCommentDto = null;
        if (patchDto.getOperation().equalsIgnoreCase("update")) {
            updatedCommentDto = commentService.updateCommentPartiallyByPostIdAndCommentId(postId, id, patchDto);
        } else if (patchDto.getOperation().equalsIgnoreCase("delete")) {
            //updatedCommentDto =  commentService.deleteParticularField(postId, id, patchDto);
        }
        return new ResponseEntity<>(updatedCommentDto, HttpStatus.OK);
    }

    @PatchMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> partiallyUpdateCommentByPostIdAndCommentIdUsingJsonPatchLib(@PathVariable("postId") Long postId,
                                                                                                  @PathVariable("id") Long id,
                                                                                                  @RequestBody JsonPatch jsonPatch) {
        CommentDto updatedCommentDto = null;
        updatedCommentDto = commentService.updateCommentPartiallyByPostIdAndCommentIdUsingJsonPatch(postId, id, jsonPatch);
        return new ResponseEntity<>(updatedCommentDto, HttpStatus.OK);
    }



}
