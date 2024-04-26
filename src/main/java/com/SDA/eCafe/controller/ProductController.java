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

import java.util.List;
import java.util.Optional;

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
        }catch(Exception error){
            return "error";
        }
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

    

}