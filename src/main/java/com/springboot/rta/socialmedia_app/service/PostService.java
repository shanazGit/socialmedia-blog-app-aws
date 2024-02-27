package com.springboot.rta.socialmedia_app.service;

import com.springboot.rta.socialmedia_app.dto.PostDto;
import com.springboot.rta.socialmedia_app.payLoad.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize);

    PostDto findPostByID(Long id);





    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);
}
