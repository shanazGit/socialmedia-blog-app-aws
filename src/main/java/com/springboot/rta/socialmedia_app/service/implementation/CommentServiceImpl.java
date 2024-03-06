package com.springboot.rta.socialmedia_app.service.implementation;

import com.springboot.rta.socialmedia_app.Entity.Comment;
import com.springboot.rta.socialmedia_app.Entity.Post;
import com.springboot.rta.socialmedia_app.dto.CommentDto;
import com.springboot.rta.socialmedia_app.dto.PatchDto;
import com.springboot.rta.socialmedia_app.exception.BlogApiException;
import com.springboot.rta.socialmedia_app.exception.ResourceNotFoundException;
import com.springboot.rta.socialmedia_app.repository.CommentRepository;
import com.springboot.rta.socialmedia_app.repository.PostRepository;
import com.springboot.rta.socialmedia_app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;


    public CommentDto createComment(long postId, CommentDto commentDto) {


        // Map Comment DTO to Comment Entity
        Comment comment = mapDtoToEntity(commentDto);
        // Fetch Post from Post Repository using PostId from Request URI
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        // Set Comment to Post
        comment.setPost(post);
        // Save Comment To DB
        Comment savedCommentEntity = commentRepository.save(comment);
        // Map Comment Entity to Comment DTO
        CommentDto saveCommentDto = mapEntityToDto(savedCommentEntity);

        return saveCommentDto;
    }
    @Override
    public List<CommentDto> getAllCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        //Map Entity to Dto
        List<CommentDto> commentDtoList = comments.stream().map(comment -> mapEntityToDto(comment)).collect(Collectors.toList());
        return commentDtoList;
    }

    @Override
    public CommentDto getCommentByPostIdAndCommentId(long postId, long id) {
        //fetch posts by postId
        Post postEntity=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",String.valueOf(postId)));
        //fetch comments bt comment id
        Comment commentEntity=commentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("comment","id",String.valueOf(id)));
        //validate comment that belongs to particular post
        if(!commentEntity.getPost().getId().equals(postEntity.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Bad Request comment not fount in the post");
        }
        //map comment entity to comment dto
        CommentDto commentDto=mapEntityToDto(commentEntity);
        return commentDto;
    }

    @Override
    public CommentDto updateCommentsByPostIdAndCommentId(Long postId) {
        return null;
    }

    @Override
    public CommentDto updateCommentPartiallyByPostIdAndCommentId(long postId, long id, PatchDto patchDto) {
        //fetch posts by postId
        Post postEntity=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",String.valueOf(postId)));
        //fetch comments bt comment id
        Comment commentEntity=commentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("comment","id",String.valueOf(id)));
        //validate comment that belongs to particular post
        if(!commentEntity.getPost().getId().equals(postEntity.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Bad Request comment not fount in the post");

        }
        partiallyUpdateCommentEntity(patchDto,commentEntity);
        Comment updatedCommentEntity=commentRepository.save(commentEntity);
        //map comment entity to dto
        CommentDto updatedCommentDto=mapEntityToDto(updatedCommentEntity);
        return updatedCommentDto;
    }

    private void partiallyUpdateCommentEntity(PatchDto patchDto, Comment commentEntity) {
        String key = patchDto.getKey();
        switch (patchDto.getKey()) {
            case  "Email" :
                commentEntity.setEmail(patchDto.getValue());
                break;
            case "Body" :
                commentEntity.setBody(patchDto.getValue());
                break;
            case "Name" :
                commentEntity.setName(patchDto.getValue());
                break;
        }
    }



    public CommentDto updateCommentsByPostIdAndCommentId(Long postId,long id,CommentDto commentDto) {
        //fetch posts by postId
        Post postEntity=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",String.valueOf(postId)));
        //fetch comments bt comment id
        Comment commentEntity=commentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("comment","id",String.valueOf(id)));
        //validate comment that belongs to particular post
        if(!commentEntity.getPost().getId().equals(postEntity.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Bad Request comment not fount in the post");
        }
        //update old comment details with new comment Dto
        commentEntity.setId(commentDto.getId());
        commentEntity.setName(commentDto.getName());
        commentEntity.setBody(commentDto.getBody());
        //save updated comment entity
        Comment updatedCommentEntity=commentRepository.save(commentEntity);
        //map comment entity to comment dto
        CommentDto updatedCommentDto=mapEntityToDto(updatedCommentEntity);

                return updatedCommentDto;
    }


    private CommentDto mapEntityToDto(Comment savedCommentEntity) {
        CommentDto commentDto=new CommentDto();
        commentDto.setId(savedCommentEntity.getId());
        commentDto.setName(savedCommentEntity.getName());
        commentDto.setEmail(savedCommentEntity.getEmail());
        commentDto.setBody(savedCommentEntity.getBody());
        return commentDto;
    }

    private Comment mapDtoToEntity(CommentDto commentDto) {
        Comment comment=new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }

}
