package com.springboot.rta.socialmedia_app.controller;

import com.springboot.rta.socialmedia_app.dto.PostDto;
import com.springboot.rta.socialmedia_app.payLoad.PostResponse;
import com.springboot.rta.socialmedia_app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
       @Autowired
   private PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
     PostDto  savedPostDto =postService.createPost(postDto);
        return new ResponseEntity(savedPostDto, HttpStatus.CREATED);
    }
    //pagination and sorting
    @GetMapping
    public PostResponse getALlPosts(
      @RequestParam(value="pageNo",defaultValue = "0",required = false) int pageNo,
      @RequestParam(value="pageSize",defaultValue = "0",required = false) int pageSize,
     @RequestParam(value="sortBy",defaultValue = "0",required = false) String sortBy,
      @RequestParam(value="sortDir",defaultValue = "0",required = false) String sortDir
    ){

    return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findPostByID(@PathVariable("id") Long id){
       return ResponseEntity.ok (postService.findPostByID(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("id")long id){
        PostDto updatedPostResponse=postService.updatePost(postDto,id);
        return ResponseEntity.ok(updatedPostResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePostById(id);
        return ResponseEntity.ok("post deleted successfully::"+id);
    }

}
