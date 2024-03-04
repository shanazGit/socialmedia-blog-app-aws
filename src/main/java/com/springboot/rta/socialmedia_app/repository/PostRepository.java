package com.springboot.rta.socialmedia_app.repository;

import com.springboot.rta.socialmedia_app.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
}
