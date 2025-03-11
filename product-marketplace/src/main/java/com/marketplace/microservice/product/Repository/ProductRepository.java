package com.marketplace.microservice.product.Repository;

import com.marketplace.microservice.product.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
