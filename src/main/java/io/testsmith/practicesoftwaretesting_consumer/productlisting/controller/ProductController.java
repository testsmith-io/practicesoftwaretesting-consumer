package io.testsmith.practicesoftwaretesting_consumer.productlisting.controller;

import io.testsmith.practicesoftwaretesting_consumer.productlisting.model.Product;
import io.testsmith.practicesoftwaretesting_consumer.productlisting.service.ProductService;
import io.testsmith.practicesoftwaretesting_consumer.productlisting.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RentalService rentalService;

    @GetMapping("/")
    public String listProducts(Model model) {
        List<Product> products = productService.getProducts(1); // Fetch first page of products
        List<Product> rentalProducts = rentalService.getRentalProducts(); // Fetch all rental products
        model.addAttribute("products", products);
        model.addAttribute("rentalProducts", rentalProducts);
        model.addAttribute("currentPage", 1); // Set initial page
        return "product-list"; // Return the full page
    }

    @GetMapping("/products/paged")
    public String getPagedProducts(@RequestParam("page") Integer page, Model model) {
        List<Product> products = productService.getProducts(page); // Fetch products for the requested page
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page); // Pass the current page to update pagination
        return "fragments/productList :: productListFragment"; // Return only the product list fragment
    }
}
