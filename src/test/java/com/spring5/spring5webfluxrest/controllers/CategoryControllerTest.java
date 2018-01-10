package com.spring5.spring5webfluxrest.controllers;

import com.spring5.spring5webfluxrest.domain.Category;
import com.spring5.spring5webfluxrest.repositories.ICategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito.*;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
        given(categoryRepository.findAll())
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
        given(categoryRepository.findById(any(String.class)))
                .willReturn(Mono.just(Category.builder().description("Category 1").build()));

        webTestClient.get()
                .uri("/api/v1/categories/1")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void createCategoryTest() throws Exception {
        given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().description("New Category").build()));

        Mono<Category> categoryMono = Mono.just(Category.builder().description("New Category").build());

        webTestClient.post()
                .uri("/api/v1/categories")
                .body(categoryMono,Category.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void updateCategoryTest() throws Exception {
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> categoryMono = Mono.just(Category.builder().description("New Category").build());

        webTestClient.put()
                .uri("/api/v1/categories/id")
                .body(categoryMono,Category.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void patchCategoryTest() throws Exception {
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> categoryMono = Mono.just(Category.builder().description("New Category").build());

        webTestClient.patch()
                .uri("/api/v1/categories/id")
                .body(categoryMono,Category.class)
                .exchange()
                .expectStatus().isOk();

        verify(categoryRepository).save(any());
    }

    @Test
    public void patchCategoryTestWithNoChanges() throws Exception {
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> categoryMono = Mono.just(Category.builder().build());

        webTestClient.patch()
                .uri("/api/v1/categories/id")
                .body(categoryMono,Category.class)
                .exchange()
                .expectStatus().isOk();

        verify(categoryRepository,never()).save(any());
    }
}