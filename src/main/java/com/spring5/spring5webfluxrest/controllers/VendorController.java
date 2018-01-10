package com.spring5.spring5webfluxrest.controllers;

import com.spring5.spring5webfluxrest.domain.Vendor;
import com.spring5.spring5webfluxrest.repositories.IVendorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {
    private final IVendorRepository vendorRepository;

    public VendorController(IVendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    Flux<Vendor> listAllVendors(){
        return vendorRepository.findAll();
    }

    @GetMapping("{id}")
    Mono<Vendor> getVendorById(@PathVariable String id){
        return vendorRepository.findById(id);
    }
}
