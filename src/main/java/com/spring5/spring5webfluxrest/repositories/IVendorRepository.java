package com.spring5.spring5webfluxrest.repositories;

import com.spring5.spring5webfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IVendorRepository extends ReactiveMongoRepository<Vendor,String> {
}
