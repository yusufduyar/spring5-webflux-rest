package com.spring5.spring5webfluxrest.controllers;

import com.spring5.spring5webfluxrest.domain.Category;
import com.spring5.spring5webfluxrest.repositories.ICategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CancellationException;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final ICategoryRepository categoryRepository;

    public CategoryController(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    Flux<Category> list() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    Mono<Category> getById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> createCategory(@RequestBody Publisher<Category> categoryStream) {
        return categoryRepository.saveAll(categoryStream).then();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Category> updateCategory(@PathVariable String id, @RequestBody Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Category> patchCategory(@PathVariable String id, @RequestBody Category category) {
        Category existingCategory = categoryRepository.findById(id).block();
        if(existingCategory.getDescription() != category.getDescription()){
            existingCategory.setDescription(category.getDescription());
            return categoryRepository.save(existingCategory);
        }
        return Mono.just(existingCategory);
    }
}
