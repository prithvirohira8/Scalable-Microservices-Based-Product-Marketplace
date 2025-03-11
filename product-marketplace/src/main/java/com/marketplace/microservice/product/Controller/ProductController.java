package com.marketplace.microservice.product.Controller;
import com.marketplace.microservice.product.Service.ProductService;
import com.marketplace.microservice.product.dto.ProductRequest;
import com.marketplace.microservice.product.dto.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    // Explicit constructor injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }
}