package com.letsplay.controllers;

import com.letsplay.dtos.ProductRequest;
import com.letsplay.entities.Product;
import com.letsplay.security.UserPrincipal;
import com.letsplay.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products") // تذكر بلي GET /products خليناها open ف السيكوريتي
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 🌍 GET /products -> Public access
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // 🔐 POST /products -> Authenticated users only
    @PostMapping
    public ResponseEntity<Product> createProduct(
            @Valid @RequestBody ProductRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser // 🚀 هاهي خدامة ناضية!
    ) {
        Product createdProduct = productService.createProduct(request, currentUser.getId());
        return ResponseEntity.ok(createdProduct);
    }

    // 🔐 PUT /products/{id} -> Restricted to owners or admins
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id,
            @Valid @RequestBody ProductRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        Product updatedProduct = productService.updateProduct(id, request, currentUser);
        return ResponseEntity.ok(updatedProduct);
    }

    // 🔐 DELETE /products/{id} -> Restricted to owners or admins
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable String id,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        productService.deleteProduct(id, currentUser);
        return ResponseEntity.ok("L'produit tmsa7 b nja7!");
    }
}
