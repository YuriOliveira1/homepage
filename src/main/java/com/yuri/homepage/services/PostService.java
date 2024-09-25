package com.yuri.homepage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuri.homepage.entities.Post;

import com.yuri.homepage.repositories.PostRepository;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Optional<Post> findById(Long id) {
        return repository.findById(id);
    }

    public void insertPost(Post post) {
        repository.save(post);
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }

    public void updatePost(Post post) {
        Optional<Post> existingPost = repository.findById(post.getId());
        if (existingPost.isPresent()) {
            Post postOriginal = existingPost.get();

            postOriginal.setPost(post.getPost());

            repository.save(postOriginal);
        } 
    }
}
