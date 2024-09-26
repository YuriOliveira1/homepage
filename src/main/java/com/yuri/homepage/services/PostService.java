package com.yuri.homepage.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuri.homepage.entities.Post;
import com.yuri.homepage.repositories.PostRepository;

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
        Optional<Post> existingPostOpt = repository.findById(post.getId());
        if (existingPostOpt.isPresent()) {
            Post existingPost = existingPostOpt.get();

            // Atualiza todos os campos necessários
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            // existingPost.setSlug(post.getSlug());
            // Você pode decidir se deseja atualizar `createdAt` ou mantê-lo como está

            repository.save(existingPost);
        }
    }
}
