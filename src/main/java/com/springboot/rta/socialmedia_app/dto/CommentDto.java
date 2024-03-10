package com.springboot.rta.socialmedia_app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    @NotNull(message="name should not be null or empty")
    @Size(min=3,max=50,message = "name should have min of 3 and maximum 50 characters")
    private String name;
    @NotNull(message="Emial should not be null or empty")
    private String email;
    @NotNull(message="comment body should not be null or empty")
    @Size(min=10,message = "comment body should have atleast 10 characters")
    private String body;
}
