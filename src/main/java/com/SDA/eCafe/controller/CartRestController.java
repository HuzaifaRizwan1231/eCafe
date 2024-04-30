package com.SDA.eCafe.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SDA.eCafe.repository.CartRepository;
import com.SDA.eCafe.model.Cart;
import com.SDA.eCafe.model.CartId;
import com.SDA.eCafe.model.Product;

@RestController
@RequestMapping("/rest")
public class CartRestController {

    private CartRepository cartRepository;

    @Autowired
    public CartRestController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @PostMapping("/cart/addToCart")
    public String addToCart(@RequestBody Cart cartRequest) {
        System.out.println("heloooooooooooooooooooooooooooooooooooo\n");
        try {
            Integer userId = cartRequest.getUserId();
            Integer productId = cartRequest.getProductId();
            Integer quantity = cartRequest.getQuantity();

            CartId cartId = new CartId(userId, productId);
            Optional<Cart> optionalExistingCart = cartRepository.findById(cartId);

            if (optionalExistingCart.isPresent()) {
                // If an existing cart is found, update its quantity
                Cart existingCart = optionalExistingCart.get();
                existingCart.setQuantity(existingCart.getQuantity() + quantity);
                cartRepository.save(existingCart);
            } else {
                // If no existing cart is found, create a new one
                Cart newCart = new Cart();
                newCart.setUserId(userId);
                newCart.setProductId(productId);
                newCart.setQuantity(quantity);
                cartRepository.save(newCart);
            }

            return "Success";
        } catch (Exception error) {
            return "Error: " + error.getMessage();
        }
    }

}