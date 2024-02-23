package com.springboot.rta.socialmedia_app.service;

import com.springboot.rta.socialmedia_app.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto findPostByID(Long id);





    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);
}
