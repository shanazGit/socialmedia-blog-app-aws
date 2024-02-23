package com.springboot.rta.socialmedia_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

      @Column(name="titile")
    private String title;
    @Column(name="description")
    private String description;
    @Column(name="content")
    private String content;

}
