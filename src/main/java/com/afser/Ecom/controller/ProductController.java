package com.afser.Ecom.controller;

import com.afser.Ecom.dto.ProductRequest;
import com.afser.Ecom.dto.ProductResponse;
import com.afser.Ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request){
        return new ResponseEntity<ProductResponse>(productService.addProduct(request),
                HttpStatus.CREATED);
    }
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest request, @PathVariable Long id){
        return productService.updateProduct(request,id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
