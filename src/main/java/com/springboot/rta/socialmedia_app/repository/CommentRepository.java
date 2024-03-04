package com.springboot.rta.socialmedia_app.repository;

import com.springboot.rta.socialmedia_app.Entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(long postId);
}
