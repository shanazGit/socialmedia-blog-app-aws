package com.springboot.rta.socialmedia_app.service.implementation;

import com.springboot.rta.socialmedia_app.dto.PostDto;
import com.springboot.rta.socialmedia_app.entity.Post;
import com.springboot.rta.socialmedia_app.exception.ResourceNotFoundException;
import com.springboot.rta.socialmedia_app.repository.PostRepository;
import com.springboot.rta.socialmedia_app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceImpl implements PostService {
    @Autowired
   private PostRepository postRepository;
    @Override
    public PostDto createPost(PostDto postDto) {
        //map postDto to Entity
        Post post=mapDtoToEntity(postDto);
        //save to db
        Post savedPost=postRepository.save(post);
        //map Entity to Dto
        PostDto savedPostDto=mapEntityTODto(savedPost);
      return savedPostDto;
    }
    @Override
    public List<PostDto> getAllPosts() {
        List<Post> allPosts= postRepository.findAll();
        //map post Entity to Dto
       List<PostDto>postDtoList =allPosts.stream().map(post-> mapEntityTODto(post)).collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public PostDto findPostByID(Long id) {

        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",String.valueOf(id)));

        PostDto postDto=mapEntityTODto(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post existingPost=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",String.valueOf(id)));
        existingPost.setContent(postDto.getContent());
        existingPost.setDescription(postDto.getDescription());
        existingPost.setTitle(postDto.getTitle());
        Post updatedPost=postRepository.save(existingPost);
        return mapEntityTODto(updatedPost);

    }

    @Override
    public void deletePostById(long id) {
       Post existingPost=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",String.valueOf(id)));
        postRepository.delete(existingPost);
    }

    private PostDto mapEntityTODto(Post post) {
        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        return postDto;
    }
    private Post mapDtoToEntity(PostDto postDto){
       Post post=new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return post;
    }


}
