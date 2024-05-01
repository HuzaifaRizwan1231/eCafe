package com.SDA.eCafe.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.SDA.eCafe.model.Cart;
import com.SDA.eCafe.model.CartId;
import com.SDA.eCafe.model.Orders;
import com.SDA.eCafe.model.Product;
import com.SDA.eCafe.repository.CartRepository;
import com.SDA.eCafe.repository.OrderRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OrderController {

    private OrderRepository orderRepository;
    private CartRepository cartRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    @GetMapping("/getOrders")
    public String getOrders(Model model) {
        List<Orders> orders = orderRepository.findAll();
        System.out.println(orders);
        model.addAttribute("orders", orders);
        return "ViewOrder"; // Assuming you have an HTML template named "ViewOrder"
    }

    @GetMapping("/orders")
    public String getOrdersByUserId(Model model, HttpServletRequest request) {
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

        if (userId != null) {
            List<Object[]> ordersWithProduct = orderRepository.findOrdersWithProductByUserId(userId);
            List<Orders> orders = new ArrayList<>();
            List<Product> products = new ArrayList<>();

            for (Object[] result : ordersWithProduct) {
                Orders order = (Orders) result[0];
                Product product = (Product) result[1];
                orders.add(order);
                products.add(product);
            }
            model.addAttribute("products", products);
            model.addAttribute("orders", orders);
        } else {
            return "redirect:/login";
        }
        return "orders";
    }

    @GetMapping("/orders/edit")
    public String editOrder(Model model, HttpServletRequest request) {
        try {
            // Retrieve userId from cookies
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

            // Check if userId is available
            if (userId == null) {
                // If userId is not available, navigate to login
                return "redirect:/login";
            }

            // Get orderId from the request parameter
            Integer orderId = Integer.parseInt(request.getParameter("orderId"));

            // Check if the orderId is associated with the userId
            List<Orders> userOrders = orderRepository.findPendingOrdersByUserId(userId);
            Orders selectedOrder = null;
            for (Orders order : userOrders) {
                if (order.getId().equals(orderId)) {
                    selectedOrder = order;
                    break;
                }
            }

            // If orderId does not exist in the user's orders, navigate to login
            if (selectedOrder == null) {
                return "redirect:/login";
            }

            // Add selectedOrder to the model
            model.addAttribute("order", selectedOrder);

            // If orderId exists in the user's orders, return the editOrder.html page
            return "editOrder";
        } catch (Exception error) {
            return "error";
        }
    }

    @PostMapping("/orders/update")
    public String updateOrder(
            @RequestParam("orderId") Integer orderId,
            @RequestParam("pickupTime") @DateTimeFormat(pattern = "HH:mm") LocalTime pickupTimeStr,
            @RequestParam("paymentMethod") String paymentMethod,
            @RequestParam("quantity") int quantity,
            Model model) {

        try {
            Time pickupTime = Time.valueOf(pickupTimeStr);

            // Retrieve order from the database based on the orderId
            Orders order = orderRepository.findById(orderId).orElse(null);
            if (order == null) {
                // If order not found, return error message
                return "redirect:/orders";
            }

            // Update order details based on the form data
            order.setPickupTime(pickupTime);
            order.setPaymentMethod(paymentMethod);
            order.setQuantity(quantity);

            // Save the updated order back to the database
            orderRepository.save(order);

            // Return success message
            model.addAttribute("title", "Updated");
            model.addAttribute("message", "Your order has been updated successfully.");
            return "orderEditDeleteSuccessful";
        } catch (Exception error) {
            return "error";
        }
    }

    @PostMapping("/orders/cancel")
    public String cancelOrder(
            @RequestParam("orderId") Integer orderId,
            Model model) {
        try {
            Orders order = orderRepository.findById(orderId).orElse(null);
            if (order == null) {
                // If order not found, return error message
                return "redirect:/orders";
            }

            order.setStatus("Canceled");
            orderRepository.save(order);

            model.addAttribute("title", "Deleted");
            model.addAttribute("message", "Your order has been deleted successfully.");
            return "orderEditDeleteSuccessful";
        } catch (Exception error) {
            return "error";
        }
    }

    @GetMapping("/placeOrder")
    public String placeOrder(@RequestParam(value = "time", required = true) @DateTimeFormat(pattern = "HH:mm") LocalTime pickupTimeStr, @RequestParam(value = "paymentMethod", required = true) String paymentMethod, HttpServletRequest request) {

        // Functions for date time
        Date date = new Date(System.currentTimeMillis());
        Time pickupTime = Time.valueOf(pickupTimeStr);
       
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
            if (userId != null) {
                List<Object[]> cartWithProducts = cartRepository.findCartOfProductByUserId(userId);
                List<Cart> cartItems = new ArrayList<>();
                List<Product> products = new ArrayList<>();
    
                for (Object[] result : cartWithProducts) {
                    Cart cartItem = (Cart) result[0];
                    Product product = (Product) result[1];
                    cartItems.add(cartItem);
                    products.add(product);
                }
                
                //NOW INSERTING THE USERS CART ITEMS INTO NEW ORDERS
                for (int i=0;i<cartItems.size();i++){
                    // Instance of order
                    Orders order = new Orders();
                    System.out.println(i+"/n");
                    order.setProductId(cartItems.get(i).getProductId());
                    order.setUserId(userId);
                    order.setQuantity(cartItems.get(i).getQuantity());
                    order.setProductPrice(products.get(i).getPrice());
                    order.setDate(date);
                    order.setStatus("Pending");
                    order.setPaymentMethod(paymentMethod);
                    order.setPickupTime(pickupTime);
                    orderRepository.save(order);

                    CartId cartId = new CartId(userId, cartItems.get(i).getProductId());
                    Optional<Cart> cart = cartRepository.findById(cartId);
                    cart.ifPresent(cartRepository::delete);
                }

            } else {
                return "redirect:/login";
            }
            return "redirect:/cart";
    }
    catch (Exception error) {
        System.out.println(error.getMessage());
        return "error";
    }
    }
}