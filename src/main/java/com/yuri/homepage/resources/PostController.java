package com.yuri.homepage.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuri.homepage.entities.Post;
import com.yuri.homepage.services.PostService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService service;

    @GetMapping
    public ResponseEntity<List<Post>> findAll() {
        List<Post> listPosts = service.findAll();
        return ResponseEntity.ok().body(listPosts);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Post> createPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content) {

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);

        try {
            service.insertPost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(post);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<Post> deletePost(@PathVariable Long id) {
        Optional<Post> existingPost = service.findById(id);
        if (existingPost.isPresent()) {
            service.deletePost(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
