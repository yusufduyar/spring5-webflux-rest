package com.spring5.spring5webfluxrest.controllers;

import com.spring5.spring5webfluxrest.domain.Vendor;
import com.spring5.spring5webfluxrest.repositories.IVendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> createNewVendor(@RequestBody Publisher<Vendor> vendorStream){
        return vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Vendor> updateVendor(@PathVariable String id,@RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("/{id}")
    Mono<Vendor> patchVendor(@PathVariable String id,@RequestBody Vendor vendor){
        Vendor existingVendor = vendorRepository.findById(id).block();
        boolean vendorChanged = false;
        if(existingVendor.getFirstName() != vendor.getFirstName())
        {
            existingVendor.setFirstName(vendor.getFirstName());
            vendorChanged = true;
        }
        if(existingVendor.getLastName() != vendor.getLastName()){
            existingVendor.setLastName(vendor.getLastName());
            vendorChanged = true;
        }
        if(vendorChanged) return vendorRepository.save(existingVendor);
        else return Mono.just(existingVendor);
    }
}
