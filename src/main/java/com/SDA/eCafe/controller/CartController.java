package com.SDA.eCafe.controller;

import com.SDA.eCafe.model.Cart;
import com.SDA.eCafe.model.Orders;
import com.SDA.eCafe.model.Product;
import com.SDA.eCafe.repository.CartRepository;
import com.SDA.eCafe.repository.ProductRepository;
import com.SDA.eCafe.service.ProductService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


//@RestController
@Controller
@RequestMapping("/")
public class CartController {

    private CartRepository cartRepository;

    @Autowired
    public CartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @GetMapping("/cart")
    public String getCartItemsByUserId(Model model, HttpServletRequest request) {
        try{
                // GET USER ID FROM COOKIES
                Integer userId = null;
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("userId")) {
                            userId = Integer.parseInt(cookie.getValue());
                            break;
                        }
                    }
                }

                
                // GET CART ITEMS WITH PRODUCTS BASED ON USER ID
                if (userId == null) {
                    List<Object[]> cartWithProducts = cartRepository.findCartOfProductByUserId(9);
                    List<Cart> cartItems = new ArrayList<>();
                    List<Product> products = new ArrayList<>();
        
                    for (Object[] result : cartWithProducts) {
                        Cart cartItem = (Cart) result[0];
                        Product product = (Product) result[1];
                        cartItems.add(cartItem);
                        products.add(product);
                    }
                    model.addAttribute("products", products);
                    model.addAttribute("cartItems", cartItems);
                } else {
                    return "redirect:/login";
                }
                return "Cart";
        }
        catch (Exception error) {
            System.out.println(error);
            return "error";
        }
    }


}

