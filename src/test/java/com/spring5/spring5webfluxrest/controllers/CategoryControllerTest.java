package com.spring5.spring5webfluxrest.controllers;

import com.spring5.spring5webfluxrest.domain.Category;
import com.spring5.spring5webfluxrest.repositories.ICategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class CategoryControllerTest {
    WebTestClient webTestClient;
    ICategoryRepository categoryRepository;
    CategoryController categoryController;

    @Before
    public void setUp() throws Exception {
        categoryRepository = Mockito.mock(ICategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void list() throws Exception {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(
                        Flux.just(
                                Category.builder().description("Category 1").build(),
                                Category.builder().description("Category 2").build()
                        )
                );

        webTestClient.get()
                .uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getById() throws Exception {
        BDDMockito.given(categoryRepository.findById(any(String.class)))
                .willReturn(Mono.just(Category.builder().description("Category 1").build()));

        webTestClient.get()
                .uri("/api/v1/categories/1")
                .exchange()
                .expectBody(Category.class);
    }

}