package com.SDA.eCafe.controller;

import com.SDA.eCafe.model.Cart;
import com.SDA.eCafe.model.CartId;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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
        try {
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
            if (userId != null) {
                List<Object[]> cartWithProducts = cartRepository.findCartOfProductByUserId(userId);
                List<Cart> cartItems = new ArrayList<>();
                List<Product> products = new ArrayList<>();

                // Variable to send totalprice for items in cart
                Integer TotalPrice = 0;

                for (Object[] result : cartWithProducts) {
                    Cart cartItem = (Cart) result[0];
                    Product product = (Product) result[1];
                    TotalPrice += product.getPrice() * cartItem.getQuantity();
                    cartItems.add(cartItem);
                    products.add(product);
                }
                model.addAttribute("products", products);
                model.addAttribute("cartItems", cartItems);
                model.addAttribute("TotalPrice", TotalPrice);
            } else {
                return "redirect:/login";
            }
            return "Cart";
        } catch (Exception error) {
            System.out.println(error);
            return "error";
        }
    }

    @GetMapping("/deleteFromCart/{id}")
    public String getProductDetail(@PathVariable("id") int productId, Model model, HttpServletRequest request) {
        try {
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

            // DELETE THE PRODUCT FROM THE CART
            if (userId != null) {
                CartId cartId = new CartId(userId, productId);
                Optional<Cart> cart = cartRepository.findById(cartId);
                cart.ifPresent(cartRepository::delete);
                // cartRepository.deleteByUserIdAndProductId(userId, productId);
            }
            return "redirect:/cart ";
        } catch (Exception error) {
            System.out.println(error.getMessage());
            return "error";
        }
    }

    @GetMapping("/updateQuantity")
    public String updateProductQuantity(@RequestParam(value = "quantity") Integer quantity,
            @RequestParam(value = "productId") Integer productId, Model model, HttpServletRequest request) {
        try {
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

            // UPDATE THE QUANTITY OF THE PRODUCT
            if (userId != null) {
                CartId cartId = new CartId(userId, productId);
                Optional<Cart> cartOptional = cartRepository.findById(cartId);
                if (cartOptional.isPresent()) {
                    Cart cart = cartOptional.get();
                    // Update the quantity of the product
                    cart.setQuantity(quantity);
                    // Save the updated cart
                    cartRepository.save(cart);
                }
            }
            return "redirect:/cart ";
        } catch (Exception error) {
            System.out.println(error.getMessage());
            return "error";
        }
    }

}
