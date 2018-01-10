package com.spring5.spring5webfluxrest.bootstrap;

import com.spring5.spring5webfluxrest.domain.Category;
import com.spring5.spring5webfluxrest.domain.Vendor;
import com.spring5.spring5webfluxrest.repositories.ICategoryRepository;
import com.spring5.spring5webfluxrest.repositories.IVendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner{
    private final ICategoryRepository categoryRepository;
    private final IVendorRepository vendorRepository;

    public Bootstrap(ICategoryRepository categoryRepository, IVendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(categoryRepository.count().block()==0){
            System.out.println(">>>>>>>>>> Started to load data with bootstrap <<<<<<<<<<<");

            categoryRepository.save(Category.builder().description("Fruits").build()).block();
            categoryRepository.save(Category.builder().description("Nuts").build()).block();
            categoryRepository.save(Category.builder().description("Breads").build()).block();
            categoryRepository.save(Category.builder().description("Meats").build()).block();
            categoryRepository.save(Category.builder().description("Eggs").build()).block();

            System.out.println("Loaded Category Count : "+ categoryRepository.count().block());

            vendorRepository.save(Vendor.builder().firstName("Yusuf").lastName("Duyar").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Melike").lastName("Duyar").build()).block();

            System.out.println("Loaded Vendor Count : "+ vendorRepository.count().block());
        }
    }
}
