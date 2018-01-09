package com.spring5.spring5webfluxrest.repositories;

import com.spring5.spring5webfluxrest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ICategoryRepository extends ReactiveMongoRepository<Category,String> {
}
