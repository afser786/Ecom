package com.afser.Ecom.service;

import com.afser.Ecom.dto.ProductRequest;
import com.afser.Ecom.dto.ProductResponse;
import com.afser.Ecom.model.Product;
import com.afser.Ecom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;


    public ProductResponse addProduct(ProductRequest request) {
        Product product = new Product();
        updateProductFromRequest(product, request);
        Product savedProduct = repo.save(product);
        return mapToProductResponse(savedProduct);
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setStockQuantity(savedProduct.getStockQuantity());
        response.setCategory(savedProduct.getCategory());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setActive(savedProduct.isActive());
        return response;
    }

    private void updateProductFromRequest(Product product, ProductRequest request) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());
    }

    public Optional<ProductResponse> updateProduct(ProductRequest request, Long id) {
       return repo.findById(id).map(existingProduct -> {
            updateProductFromRequest(existingProduct, request);
            Product savedProduct = repo.save(existingProduct);
            return mapToProductResponse(savedProduct);
        });
    }

    public List<ProductResponse> getProduct() {
        return repo.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public boolean removeProduct(Long id) {
        return repo.findById(id).map(product -> {
            product.setActive(false);
            repo.save(product);
            return true;
        }).orElse(false);
    }

    public List<ProductResponse> searchProduct(String keyword) {
        return repo.searchProduct(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
}