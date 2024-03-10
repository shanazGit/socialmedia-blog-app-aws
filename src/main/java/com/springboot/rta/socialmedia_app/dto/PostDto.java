package com.springboot.rta.socialmedia_app.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min=5,message = "title should have atleat 5 characters")
    private String title;

    @Size(min=10,message = "post description should have atleat 10 characters")
    private String description;
    @NotEmpty
    private String content;



}
