package com.SDA.eCafe.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.SDA.eCafe.model.User;
import com.SDA.eCafe.repository.CartRepository;
import com.SDA.eCafe.repository.OrderRepository;
import com.SDA.eCafe.repository.ProductRepository;
import com.SDA.eCafe.repository.UserRepository;
import com.SDA.eCafe.service.ProductService;

import jakarta.transaction.Transactional;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OrderController {

    private OrderRepository orderRepository;
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private ProductService productService;
    private UserRepository userRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, UserRepository userRepository, ProductService productService,ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;

    }

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

    @GetMapping("/CurrentOrders")
    public String getPendingOrders(Model model, HttpServletRequest request) {
        if ("Clerk".equals(getRoleFromCookies(request)) || "Admin".equals(getRoleFromCookies(request))){
            List<Orders> orders = orderRepository.findAllPendingOrders();
            model.addAttribute("orders", orders);
            String role = getRoleFromCookies(request);
            model.addAttribute("role", role);
            return "ViewOrder"; // Assuming you have an HTML template named "orders"
        }
        else{
            return "redirect:/login";
        }
    }

    @GetMapping("/OrderHistory")
    public String getOrderHistory(Model model, HttpServletRequest request) {
        try {

            if ("Manager".equals(getRoleFromCookies(request)) || "Admin".equals(getRoleFromCookies(request))) {
                List<Orders> orders = orderRepository.findAllCompletedOrCanceledOrders();
                model.addAttribute("orders", orders);
                String role = getRoleFromCookies(request);
                model.addAttribute("role", role);
                return "OrderHistory";
            }
            else{
                return "redirect:/login";
            }

        } catch (Exception e) {
            return "e";
        }

    }

    @GetMapping("/ProductsCategory/{category}")
    public String getProductBYCategory(@PathVariable("category") String category, Model model, HttpServletRequest request) {
        try {
            model.addAttribute("product", productRepository.findByCategory(category));
            model.addAttribute("productCategory", category);
            String role = getRoleFromCookies(request);
            model.addAttribute("role", role);
            return "Product";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/ProductsCategory2/{category}")
    public String getProductBYCategory2(@PathVariable("category") String category, Model model, HttpServletRequest request) {
        try {
            if ("Manager".equals(getRoleFromCookies(request)) || "Admin".equals(getRoleFromCookies(request))) {
                model.addAttribute("product", productRepository.findByCategory(category));
                model.addAttribute("productCategory", category);
                String role = getRoleFromCookies(request);
                model.addAttribute("role", role);
                return "ManageMenu";
            }
            else{
                return "redirect:/login";
            }
        } catch (Exception e) {
            return "e";
        }
    }

    @GetMapping("/orderDetails/{id}")
    public String getOrderDetail(@PathVariable("id") int productId, Model model, HttpServletRequest request) {
        try {
            model.addAttribute("product", productService.getProductById(productId));
            String role = getRoleFromCookies(request);
            model.addAttribute("role", role);
            return "OrderDetail"; // Return the name of your HTML template file
        } catch (Exception error) {

            System.out.println("error " + error);
            return "error"; // Handle other exceptions appropriately
        }
    }


    @GetMapping("/getproduct2")
        public String getProductByCategory2(Model model, HttpServletRequest request) {
        try {
            if ("Manager".equals(getRoleFromCookies(request)) || "Admin".equals(getRoleFromCookies(request))) {
                model.addAttribute("product",productRepository.findByCategory("Fast Food"));
                model.addAttribute("productCategory", "Fast Food");
                String role = getRoleFromCookies(request);
                model.addAttribute("role", role);
                return "ManageMenu"; // Return the name of your HTML template file
            }
            else{
                return "redirect:/login";
            }
        } catch (Exception error) {

            System.out.println("error " + error);
            return "error"; // Handle other exceptions appropriately
        }
    }

    @GetMapping("/editProduct/{id}")
    public String showEditProductForm(@PathVariable("id") int productId, Model model, HttpServletRequest request) {
        try {
            if ("Manager".equals(getRoleFromCookies(request)) || "Admin".equals(getRoleFromCookies(request))) {
                // Get the product from the database
                Product product = productService.getProductById(productId);
                
                // Add the product and ID to the model
                model.addAttribute("product", product);
                model.addAttribute("ID", productId);
                String role = getRoleFromCookies(request);
                model.addAttribute("role", role);
        
                return "EditProduct"; // This should be the name of your HTML file
            }
            else{   
                return "redirect:/login";
            }
        } catch (Exception e) {
            return "error";
            // Handle exceptions appropriately
        }

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

    @GetMapping("/CreateProduct")
    public String addProduct(Model model, HttpServletRequest request) {
        try {
            if ("Manager".equals(getRoleFromCookies(request)) || "Admin".equals(getRoleFromCookies(request))) {
                String role = getRoleFromCookies(request);
                model.addAttribute("role", role);
                return "AddProduct"; // This should be the name of your HTML file
            }
            else{
                return "redirect:/login";
            }
            // Get the product from the databas
        } catch (Exception e) {
            // Handle exceptions appropriately
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

    @GetMapping("/addProduct")
public String addProduct(@ModelAttribute Product product) {
    // System.out.println("\n\n\n--------------------------------");
    // System.out.println(product.getCategory());
    // System.out.println("--------------------------------\n\n\n");
    productService.saveProduct(product);
    return "redirect:/getproduct2"; // Redirect to the desired page after adding the product
    // return "/AddProduct"; // Redirect to the desired page after adding the product
}

    



    @PostMapping("/ManageMenu/{id}")
    public String updateProduct(@PathVariable("id") int productId, @ModelAttribute("product") Product updatedProduct, Model model , HttpServletRequest request) {
        try {
            if ("Manager".equals(getRoleFromCookies(request)) || "Admin".equals(getRoleFromCookies(request))) {
                
                System.out.println("--------------------------------------------------Yes");
                // Check if the product exists
                Product existingProduct = productService.getProductById(productId);
                
                // If the product doesn't exist, create a new one
                if (existingProduct == null) {
                    existingProduct = new Product();
                    existingProduct.setID(productId);
                }
        
                // Update the existing product with the values from the updatedProduct
                existingProduct.setName(updatedProduct.getName());
                existingProduct.setDescription(updatedProduct.getDescription());
                existingProduct.setStatus(updatedProduct.getStatus());
                existingProduct.setPrice(updatedProduct.getPrice());
                existingProduct.setCategory(updatedProduct.getCategory());
        
                // Save the product to the database
                productService.saveProduct(existingProduct);
                
                return "redirect:/getproduct2"; // Redirect to the ManageMenu page for the updated product
            }
            else{
                return "redirect:/login";
            }
        } catch (Exception error) {
            System.out.println("--------------------------------------------------Yes");
            System.out.println("error " + error);
            return "error"; // Handle other exceptions appropriately
        }
    }
    
    @PostMapping("/DeleteItem/{category}/{id}")
    public String deleteItem(@PathVariable("id") int productId, @PathVariable("category") String category, HttpServletRequest request) {
        try {
            if ("Manager".equals(getRoleFromCookies(request)) || "Admin".equals(getRoleFromCookies(request))) {
                
                System.out.println("----------------------------------");
                System.out.println(category);
                // Check if the product exists
                Product productToDelete = productService.getProductById(productId);
                // If the product exists, delete it
                if (productToDelete != null) {
                    productService.deleteProductById(productId);
                }
    
                if ("Fast Food".equals(category)){
                    return "redirect:/ProductsCategory2/Fast%20Food";
                }
                if ("Desi Food".equals(category)){
                    return "redirect:/ProductsCategory2/Desi%20Food";
                }
                if ("Cold Drinks".equals(category)){
                    return "redirect:/ProductsCategory2/Cold%20Drinks";
                }
               
                    return "redirect:/ProductsCategory2/Hot%20Drinks";
                
                // Redirect to the ManageMenu page
            }
            else{
                return "redirect:/login";
            }
        } catch (Exception error) {
            // Log the error
            System.out.println("Error deleting product: " + error.getMessage());
            // Redirect to the error page
            return "error";
        }
    }
    
    




@GetMapping("/BreakFastTime/{time}")
@Transactional
public String updateStatus(@RequestParam("time") LocalTime time, 
                           Model model) {
    try {
    
        return "BreakFast";
    } catch (Exception error) {
        // Handle errors appropriately
        System.out.println("Error: " + error);
        return "error";
    }
}




@GetMapping("/updateStatus/{id}")
@Transactional
public String updateStatus(@PathVariable("id") int productId, 
                           @RequestParam("status") String status, 
                           Model model) {
    try {
        // Call the service method to update the status
        model.addAttribute("product", productRepository.updateProductStatus(status,productId));
        // Redirect to a success page or return a success message
        return "redirect:/getproduct";
    } catch (Exception error) {
        // Handle errors appropriately
        System.out.println("Error: " + error);
        return "error";
    }
}


    @GetMapping("/getproduct")
        public String getProductByCategory(Model model) {
        try {
            model.addAttribute("product",productRepository.findByCategory("Fast Food"));
            model.addAttribute("productCategory", "Fast Food");
            return "Product"; // Return the name of your HTML template file
        } catch (Exception error) {

            System.out.println("error " + error);
            return "error"; // Handle other exceptions appropriately
        }
    }





    @GetMapping("/CompleteOrder/{Id}")
    @Transactional
    public String completeOrder(@PathVariable("Id") int Id, Model model) {
        try {
            orderRepository.updateOrderStatusToCompleted(Id);
            return "redirect:/CurrentOrders";
        } catch (Exception e) {
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

