package com.spring5.spring5webfluxrest.controllers;

import com.spring5.spring5webfluxrest.domain.Vendor;
import com.spring5.spring5webfluxrest.repositories.IVendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class VendorControllerTest {
    WebTestClient webTestClient;
    IVendorRepository vendorRepository;
    VendorController vendorController;

    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(IVendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void listAllVendors() throws Exception {
        BDDMockito.given(vendorRepository.findAll()).willReturn(
                Flux.just(
                        Vendor.builder().firstName("Yusuf").lastName("Duyar").build(),
                        Vendor.builder().firstName("Melike").lastName("Duyar").build()
                )
        );

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getVendorById() throws Exception {
        BDDMockito.given(vendorRepository.findById(any(String.class)))
                .willReturn(Mono.just(Vendor.builder().firstName("Yusuf").lastName("Duyar").build()));

        webTestClient.get()
                .uri("/api/v1/vendors/1")
                .exchange()
                .expectBody();
    }

}