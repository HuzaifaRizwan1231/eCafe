package com.SDA.eCafe.controller;

import com.SDA.eCafe.model.Product;
import com.SDA.eCafe.repository.ProductRepository;
import com.SDA.eCafe.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;


//@RestController
@Controller
@RequestMapping("/")
public class ProductController {

    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String getAllProducts(Model model) {
        try {
            List<Product> products = productRepository.findAll();
            System.out.println(products);
            model.addAttribute("products", products);
            return "index";
        } catch (Exception error) {
            return "error";
        }
    }

    @GetMapping("/products/search")
    public String searchAndFilterProducts(@RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "minPrice", required = false) Integer minPrice,
            @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
            Model model) {
        try {
            List<Product> filteredProducts;

            // Check if a search query is provided
            if (query != null && !query.isEmpty()) {
                // Perform search based on the query
                String[] keywords = query.split("\\s+");
                Set<Product> uniqueProducts = new HashSet<>();
                for (String keyword : keywords) {
                    List<Product> products = productRepository.findProductsByKeywordIgnoreCase(keyword);
                    uniqueProducts.addAll(products);
                }
                filteredProducts = new ArrayList<>(uniqueProducts);
            } else {
                // If no search query is provided, fetch all products
                filteredProducts = productRepository.findAll();
            }

            // Apply filter if minPrice and maxPrice parameters are provided
            if (minPrice != null && maxPrice != null) {
                // if minPrice and maxPrice has invalid value
                if (minPrice > maxPrice || minPrice < 0) {
                    throw new IllegalArgumentException("Invalid price range: minPrice cannot be greater than maxPrice");
                }
                // Filter products based on price range
                filteredProducts = filterProductsByPrice(filteredProducts, minPrice, maxPrice);
            }

            // Add filtered products to the model
            model.addAttribute("products", filteredProducts);
            model.addAttribute("query", query);

            // Return the view name
            return "search";
        } catch (Exception error) {
            // Handle any exceptions
            return "error";
        }
    }

    // Helper method to filter products by price range
    private List<Product> filterProductsByPrice(List<Product> products, int minPrice, int maxPrice) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    

    @GetMapping("/productdetails/{id}")
    public String getProductDetail(@PathVariable("id") int productId, Model model) {
        try {
            Product product = productService.getProductById(productId);
            System.out.println(product);
            model.addAttribute("product", product);
            return "productDetails";
        }catch(Exception error){

            return "error";
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        try {
            return "LoginRegister";
        }catch(Exception error){

            return "error";
        }
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        try {
            return "Cart";
        }catch(Exception error){

            return "error";
        }
    }

}