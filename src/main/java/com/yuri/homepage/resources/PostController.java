package com.yuri.homepage.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        Optional<Post> optionalPost = service.findById(id); // Busca o post pelo ID

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get(); // Obtém o post do Optional
            model.addAttribute("post", post);
            return "post"; // Nome do template que exibe o post
        } else {
            // Se o post não for encontrado, você pode redirecionar para uma página de erro ou uma página inicial
            return "redirect:/posts"; // Redireciona para a lista de posts
        }
    }

    @GetMapping("/posts")
    public String getPosts(Model model) {
        List<Post> posts = service.findAll(); // Busca todos os posts, ordenados pela data de criação
        model.addAttribute("posts", posts);
        return "post-list"; // Nome do template que lista os posts
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

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        updatedPost.setId(id); // Assegura que o ID no objeto atualizado seja o correto
        try {
            service.updatePost(updatedPost);
            return ResponseEntity.ok(updatedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
