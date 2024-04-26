package com.SDA.eCafe.controller;

import com.SDA.eCafe.model.Product;
import com.SDA.eCafe.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
@Controller
@RequestMapping("/")
public class ProductController {

    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

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
}