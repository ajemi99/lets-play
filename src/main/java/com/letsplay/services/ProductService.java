package com.letsplay.services;

import com.letsplay.dtos.ProductRequest;
import com.letsplay.entities.Product;
import com.letsplay.repositories.ProductRepository;
import com.letsplay.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 1. GET ALL (Public)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. CREATE PRODUCT (Authenticated Users Only)
    public Product createProduct(ProductRequest request, String userId) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .userId(userId) // كنربطو المنتج ب الـ Id ديال الـ user لي صاوبو
                .build();
        return productRepository.save(product);
    }

    // 3. UPDATE PRODUCT (Owner or Admin Only)
    public Product updateProduct(String productId, ProductRequest request, UserPrincipal currentUser) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produit introuvable!"));

        // 🔐 الـ Verification: واش المكونيكتي هو مول المنتج أولا Admin؟
        if (!product.getUserId().equals(currentUser.getId()) && !currentUser.getRole().equals("ADMIN")) {
            throw new RuntimeException("Ma 3ndksh l'7aq t-modifier had l'produit!");
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        return productRepository.save(product);
    }

    // 4. DELETE PRODUCT (Owner or Admin Only)
    public void deleteProduct(String productId, UserPrincipal currentUser) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produit introuvable!"));

        // 🔐 نفس الـ Verification ديال السيكوريتي
        if (!product.getUserId().equals(currentUser.getId()) && !currentUser.getRole().equals("ADMIN")) {
            throw new RuntimeException("Ma 3ndksh l'7aq t-msa7 had l'produit!");
        }

        productRepository.deleteById(productId);
    }
}