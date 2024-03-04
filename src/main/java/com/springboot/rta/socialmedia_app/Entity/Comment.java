package com.springboot.rta.socialmedia_app.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="coments")
public class Comment {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;


        //name
        private String name;
        //email
        private String email;
        //body
        private String body;

        //Mapping b/w Comments to Post
        //Comments table is managing relationship b/w posts and comments table
        @ManyToOne
        @JoinColumn(name = "post_id", nullable = false)
        private Post post;

    }
