package com.spring5.spring5webfluxrest.controllers;

import com.spring5.spring5webfluxrest.domain.Vendor;
import com.spring5.spring5webfluxrest.repositories.IVendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
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
        given(vendorRepository.findAll()).willReturn(
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
        given(vendorRepository.findById(any(String.class)))
                .willReturn(Mono.just(Vendor.builder().firstName("Yusuf").lastName("Duyar").build()));

        webTestClient.get()
                .uri("/api/v1/vendors/1")
                .exchange()
                .expectBody();
    }

    @Test
    public void createVendorTest() throws Exception {
        given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono =Mono.just(Vendor.builder().firstName("Yusuf").lastName("Duyar").build());

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendorMono,Vendor.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void updateVendorTest() throws Exception {
        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono =Mono.just(Vendor.builder().firstName("Yusuf").lastName("Duyar").build());

        webTestClient.put()
                .uri("/api/v1/vendors/iasdasdd")
                .body(vendorMono,Vendor.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void patchVendorTest() throws Exception {
        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().build()));
        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Yusuf").lastName("Duyar").build());

        webTestClient.patch()
                .uri("/api/v1/vendors/asdas")
                .body(vendorMono,Vendor.class)
                .exchange()
                .expectStatus().isOk();

        verify(vendorRepository).save(any());
    }

    @Test
    public void patchVendorWithNoChangesTest() throws Exception {
        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().build()));
        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().build());

        webTestClient.patch()
                .uri("/api/v1/vendors/asdas")
                .body(vendorMono,Vendor.class)
                .exchange()
                .expectStatus().isOk();

        verify(vendorRepository,never()).save(any());
    }
}