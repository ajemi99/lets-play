package com.letsplay.repositories;

import com.letsplay.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    
    // Had la méthode tahiya n-zydoha daba bach t-khalina mn be3d 
    // njibo ghir les produits li kymlek-hom wa7d l'user précis (One-to-Many)
    List<Product> findByUserId(String userId);
}
