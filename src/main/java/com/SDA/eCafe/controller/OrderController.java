package com.SDA.eCafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalTime;
import java.util.List;

import com.SDA.eCafe.model.Orders;
import com.SDA.eCafe.model.Product;
import com.SDA.eCafe.repository.OrderRepository;
import com.SDA.eCafe.repository.ProductRepository;
import com.SDA.eCafe.service.ProductService;

import jakarta.transaction.Transactional;

@Controller
public class OrderController {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private ProductService productService;

    @Autowired
    public OrderController(OrderRepository orderRepository, ProductService productService,ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.productRepository = productRepository;

    }

    @GetMapping("/CurrentOrders")
    public String getOrders(Model model) {
        List<Orders> orders = orderRepository.findAllPendingOrders();
        model.addAttribute("orders", orders);
        return "ViewOrder"; // Assuming you have an HTML template named "orders"
    }

    @GetMapping("/OrderHistory")
    public String getOrderHistory(Model model) {
        try {

            List<Orders> orders = orderRepository.findAllCompletedOrCanceledOrders();
            model.addAttribute("orders", orders);
            return "OrderHistory";

        } catch (Exception e) {
            return "e";
        }

    }

    @GetMapping("/ProductsCategory/{category}")
    public String getProductBYCategory(@PathVariable("category") String category, Model model) {
        try {
            model.addAttribute("product", productRepository.findByCategory(category));
            model.addAttribute("productCategory", category);
            return "Product";
        } catch (Exception e) {
            return "e";
        }
    }

    @GetMapping("/ProductsCategory2/{category}")
    public String getProductBYCategory2(@PathVariable("category") String category, Model model) {
        try {
            model.addAttribute("product", productRepository.findByCategory(category));
            model.addAttribute("productCategory", category);
            return "ManageMenu";
        } catch (Exception e) {
            return "e";
        }
    }

    @GetMapping("/orderDetails/{id}")
    public String getOrderDetail(@PathVariable("id") int productId, Model model) {
        try {
            model.addAttribute("product", productService.getProductById(productId));
            return "OrderDetail"; // Return the name of your HTML template file
        } catch (Exception error) {

            System.out.println("error " + error);
            return "error"; // Handle other exceptions appropriately
        }
    }


    @GetMapping("/getproduct2")
        public String getProductByCategory2(Model model) {
        try {
            model.addAttribute("product",productRepository.findByCategory("Fast Food"));
            model.addAttribute("productCategory", "Fast Food");
            return "ManageMenu"; // Return the name of your HTML template file
        } catch (Exception error) {

            System.out.println("error " + error);
            return "error"; // Handle other exceptions appropriately
        }
    }

    @GetMapping("/editProduct/{id}")
    public String showEditProductForm(@PathVariable("id") int productId, Model model) {
        try {
            // Get the product from the database
            Product product = productService.getProductById(productId);
            
            // Add the product and ID to the model
            model.addAttribute("product", product);
            model.addAttribute("ID", productId);
    
            return "EditProduct"; // This should be the name of your HTML file
        } catch (Exception e) {
            // Handle exceptions appropriately
            return "error";
        }
    }

    @GetMapping("/CreateProduct")
    public String addProduct() {
        try {
            // Get the product from the databas
            return "AddProduct"; // This should be the name of your HTML file
        } catch (Exception e) {
            // Handle exceptions appropriately
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
    public String updateProduct(@PathVariable("id") int productId, @ModelAttribute("product") Product updatedProduct, Model model) {
        try {
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
        } catch (Exception error) {
            System.out.println("--------------------------------------------------Yes");
            System.out.println("error " + error);
            return "error"; // Handle other exceptions appropriately
        }
    }
    
    @PostMapping("/DeleteItem/{category}/{id}")
    public String deleteItem(@PathVariable("id") int productId, @PathVariable("category") String category) {
        try {

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





}
