package com.SDA.eCafe.controller;

import com.SDA.eCafe.model.Cart;
import com.SDA.eCafe.model.Orders;
import com.SDA.eCafe.model.Product;
import com.SDA.eCafe.model.User;
import com.SDA.eCafe.repository.ProductRepository;
import com.SDA.eCafe.repository.UserRepository;
import com.SDA.eCafe.service.ProductService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


//@RestController
@Controller
@RequestMapping("/")
public class ProductController {

    private ProductRepository productRepository;
    private UserRepository userRepository;


    @Autowired
    public ProductController(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    private ProductService productService;

     public String getRoleFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Integer userId = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    userId = Integer.parseInt(cookie.getValue());
                    break;
                }
            }
            Optional<User> loggedInUser = userRepository.findById(userId);
            if (!loggedInUser.isEmpty()) {
                System.out.println(loggedInUser.get().getRole());
                return loggedInUser.get().getRole();
            }
        }
        return "noUser";
    }

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

    @GetMapping("/analytics")
    public String showAnalytics(Model model, HttpServletRequest request) {
        try {
            if ("Admin".equals(getRoleFromCookies(request))|| "Manager".equals(getRoleFromCookies(request))) {
                List<Object[]> productsWithTotalSsle = productRepository.getAllProductsWithSale();
                List<String> productNames = new ArrayList<>();
                List<Long> productPrices = new ArrayList<>();        
    
                System.out.println("\n\n\n");
    
                for (Object[] result : productsWithTotalSsle) {
                    String productName = (String) result[0];
                    Long productPrice = (Long) result[1];
                    productNames.add(productName);
                    productPrices.add(productPrice);
                    System.out.println("----------------------------------");
                    System.out.println(productName +" "+ productPrice);
                }
                String role = getRoleFromCookies(request);
                model.addAttribute("role", role);
                model.addAttribute("productNames", productNames);
                model.addAttribute("productPrices", productPrices);
                System.out.println("\n\n\n");
                return "analytics";
            }
            else{
                return "redirect:/login";
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
            System.out.println("\n\n\n");
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
        } catch (Exception error) {

            return "error";
        }
    }


 @PostMapping("/product/addToCart")
    public String addToCart(@RequestBody String entity) {
        try {
            System.out.println("agya tu jawan ho kr");
            return "nothing";
        } catch (Exception error) {
           return "error";
        }
    }


}

