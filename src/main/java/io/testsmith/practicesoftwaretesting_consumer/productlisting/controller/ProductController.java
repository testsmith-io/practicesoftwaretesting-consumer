package io.testsmith.practicesoftwaretesting_consumer.productlisting.controller;

import io.testsmith.practicesoftwaretesting_consumer.productlisting.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products22")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "product-list";
    }
}