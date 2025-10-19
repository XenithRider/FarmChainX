package com.farmchain.farmchain.service;

import com.farmchain.farmchain.model.Product;
import com.farmchain.farmchain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductService {

    private final ProductRepository productRepository ;


    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository ;
    }

    @Transactional
    public Product saveProduct(Product product){
        System.out.println("[Service] Saving product :" + product.getCropName());
        Product saved = productRepository.save(product);

        System.out.println("[Service] After save, ID ="+ saved.getId());
        return saved;
    }

    public List<Product> getProductsByFarmerId( Long farmerId){
        return productRepository.findByFarmerId(farmerId);
    }

    public Product getProductById(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("Product not found")) ;
    }



}
