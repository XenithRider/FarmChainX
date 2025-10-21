package com.farmchain.farmchain.controller;

import com.farmchain.farmchain.model.Product;
import com.farmchain.farmchain.model.User;
import com.farmchain.farmchain.repository.UserRepository;
import com.farmchain.farmchain.service.ProductService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final UserRepository userRepository;

    public ProductController(ProductService productService, UserRepository userRepository) {
        this.productService = productService;
        this.userRepository = userRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<Product> uploadProduct(
            @RequestParam String cropName,
            @RequestParam String soilType,
            @RequestParam String pesticide,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate harvestDate,
            @RequestParam String gpsLocation,
            @RequestParam Long farmerId,
            @RequestParam("image") MultipartFile imageFile
    ) throws IOException {

        System.out.println(" [Controller] Entered /upload endpoint");

        if (imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";
        File folder = new File(uploadDir);
        if (!folder.exists() && !folder.mkdirs()) {
            throw new IOException("Failed to create upload directory");
        }

        String imagePath = uploadDir + File.separator + System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        imageFile.transferTo(new File(imagePath));

        User farmer = userRepository.findById(farmerId)
                .orElseThrow(() -> new IllegalArgumentException("Farmer not found"));
        System.out.println("🔥 [Controller] Farmer found: " + farmer.getEmail());

        Product product = new Product();
        product.setCropName(cropName);
        product.setSoilType(soilType);
        product.setPesticides(pesticide);
        product.setHarvestDate(harvestDate);
        product.setGpsLocation(gpsLocation);
        product.setImagePath(imagePath);
        product.setQualityGrade("Pending");
        product.setConfidenceScore(0.0);
        product.setFarmer(farmer);

        Product saved = productService.saveProduct(product);
        System.out.println("[Controller] Product saved with ID: " + saved.getId());

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<Product>> getProductsByFarmer(@PathVariable Long farmerId) {
        List<Product> products = productService.getProductsByFarmerId(farmerId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }
}