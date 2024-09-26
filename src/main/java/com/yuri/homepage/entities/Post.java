package com.yuri.homepage.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // Campo para o título do post

    private String content; // Campo para o conteúdo do post

    private String slug; // Campo para o slug do post

    @Column(name = "created_at", updatable = false)
    private String createdAt; // Campo para a data de criação do post

    @PrePersist
    protected void onCreate() {
        this.createdAt = convertToString(LocalDateTime.now()); // Armazena a data como String
        this.slug = generateSlug(this.title); // Gera o slug a partir do título
    }

    // Função para converter LocalDateTime para String
    private String convertToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    // Função para gerar o slug a partir do título
    private String generateSlug(String title) {
        return title.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("-$", "");
    }

}
