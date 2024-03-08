package com.springboot.rta.socialmedia_app.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.springboot.rta.socialmedia_app.Entity.Comment;
import com.springboot.rta.socialmedia_app.Entity.Post;
import com.springboot.rta.socialmedia_app.dto.CommentDto;
import com.springboot.rta.socialmedia_app.dto.PatchDto;
import com.springboot.rta.socialmedia_app.exception.BlogApiException;
import com.springboot.rta.socialmedia_app.exception.ResourceNotFoundException;
import com.springboot.rta.socialmedia_app.repository.CommentRepository;
import com.springboot.rta.socialmedia_app.repository.PostRepository;
import com.springboot.rta.socialmedia_app.service.CommentService;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper modelMapper;


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
    public CommentDto updateCommentsByPostIdAndCommentId(Long postId, long id, CommentDto commentDto) {
        //fetch posts by postId
        Post postEntity=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",String.valueOf(postId)));
        //fetch comments bt comment id
        Comment commentEntity=commentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("comment","id",String.valueOf(id)));
        //validate comment that belongs to particular post
        if(!commentEntity.getPost().getId().equals(postEntity.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Bad Request comment not fount in the post");

        }
        // Update old Comment Details With new Comment Dto
        commentEntity.setName(commentDto.getName());
        commentEntity.setEmail(commentDto.getEmail());
        commentEntity.setBody(commentDto.getBody());

        //Save Updated Comment Entity
        Comment updatedCommentEntity  = commentRepository.save(commentEntity);

        // Map Comment Entity to Comment DTO
        CommentDto updatedCommentDto = mapEntityToDto(updatedCommentEntity);

        return updatedCommentDto;
    }

    @Override
    public CommentDto updateCommentPartiallyByPostIdAndCommentId(long postId, long id, PatchDto patchDto) {
        Post postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

        //Fetch Comment by CommentId
        Comment commentEntity = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(id)));

        // validate comment belongs to that Particular Post
        if (!commentEntity.getPost().getId().equals(postEntity.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Bad Request Comment Not Found in Post");
        }


        partiallyUpdateCommentEntity(patchDto, commentEntity);

        Comment updatedCommentEntity  = commentRepository.save(commentEntity);

        // Map Comment Entity to Comment DTO
        CommentDto updatedCommentDto = mapEntityToDto(updatedCommentEntity);

        return updatedCommentDto;
    }

    @Override
    public CommentDto updateCommentPartiallyByPostIdAndCommentIdUsingJsonPatch(Long postId, Long id, JsonPatch jsonPatch) {
        //fetch posts by postId
        Post postEntity=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",String.valueOf(postId)));
        //fetch comments bt comment id
        Comment commentEntity=commentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("comment","id",String.valueOf(id)));
        //validate comment that belongs to particular post
        if(!commentEntity.getPost().getId().equals(postEntity.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Bad Request comment not fount in the post");

        }
        CommentDto commentDto=mapEntityToDto(commentEntity);
        try {
            commentDto=applyPatchToComment(jsonPatch,commentDto);
        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Comment updatedCommentEntity = mapDtoToEntity(commentDto);
        updatedCommentEntity.setId(commentEntity.getId());
        updatedCommentEntity.setPost(postEntity);
        commentRepository.save(updatedCommentEntity);
        return commentDto;
    }
    private CommentDto mapEntityToDto(Comment savedCommentEntity) {
        return  modelMapper.map(savedCommentEntity, CommentDto.class);
    }

    private Comment mapDtoToEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }

    private CommentDto applyPatchToComment(
            JsonPatch patch, CommentDto commentDto)throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode patched= patch.apply(objectMapper.convertValue(commentDto,JsonNode.class));
        return objectMapper.treeToValue(patched,CommentDto.class);
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




    }






