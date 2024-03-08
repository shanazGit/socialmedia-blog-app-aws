package com.springboot.rta.socialmedia_app.service.implementation;

import com.springboot.rta.socialmedia_app.dto.PostDto;
import com.springboot.rta.socialmedia_app.Entity.Post;
import com.springboot.rta.socialmedia_app.exception.ResourceNotFoundException;
import com.springboot.rta.socialmedia_app.payLoad.PostResponse;
import com.springboot.rta.socialmedia_app.repository.PostRepository;
import com.springboot.rta.socialmedia_app.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
   private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
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
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable;
        if(sortBy!=null && sortDir!=null){
            Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending()
                    :Sort.by(sortBy).descending();
            pageable=PageRequest.of(pageNo,pageSize, sort);
        }else{
            pageable =PageRequest.of(pageNo,pageSize);
        }

       // List<Post> allPosts= postRepository.findAll();
       Page<Post>posts = postRepository.findAll(pageable);
       List<Post> postList=posts.getContent();
        //map post Entity to Dto
      // List<PostDto>postDtoList =allPosts .stream().map(post-> mapEntityTODto(post)).collect(Collectors.toList());
        List<PostDto>postDtoList =postList .stream().map(post-> mapEntityTODto(post)).collect(Collectors.toList());
        //customise postResponse resource
        PostResponse postResponse= PostResponse
                .builder()
                .content(postDtoList)
                .pageNo(posts.getNumber())
                .pageSize(posts.getSize())
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .isLastPage(posts.isLast())
                .build();

        return postResponse;
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
       return  modelMapper.map(post,PostDto.class);

    }
    private Post mapDtoToEntity(PostDto postDto){
        return modelMapper.map(postDto,Post.class);

    }


}
