package io.testsmith.productlisting;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.core.model.annotations.PactDirectory;
import io.testsmith.practicesoftwaretesting_consumer.PracticesoftwaretestingConsumerApplication;
import io.testsmith.practicesoftwaretesting_consumer.productlisting.model.Product;
import io.testsmith.practicesoftwaretesting_consumer.productlisting.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = PracticesoftwaretestingConsumerApplication.class)
@PactDirectory("src/test/resources/pacts")
public class PactConsumer2Test {

    @Autowired
    private ProductService productService;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("api.baseUrl", () -> "http://localhost:8081");
    }

    @Test
//	@Pact(provider = "ProductProvider", consumer = "ProductConsumer")
    public void testGetProductsFromProvider(MockServer mockServer) {

        // Log the current properties
        System.out.println("api.baseUrl system property: " + System.getProperty("api.baseUrl"));
        System.out.println("Spring Environment api.baseUrl: " + mockServer.getUrl());

        // Ensure base URL is set correctly
        System.setProperty("api.baseUrl", mockServer.getUrl());

        // Test using the service configured with the mock server URL
        List<Product> products = productService.getProducts();

        // Additional check to ensure products list is not empty
        assertNotNull(products, "Products list should not be null");
        assertTrue(products.size() > 0, "Products list should not be empty");

        // Assertions to verify the correct interaction with the mock server
        assertEquals(2, products.size(), "Product list size should be 2");
        assertEquals("Product A", products.get(0).getName(), "First product name should be 'Product A'");
        assertEquals(99.99, products.get(0).getPrice(), "First product price should be 99.99");
        assertEquals("Product B", products.get(1).getName(), "Second product name should be 'Product B'");
        assertEquals(49.99, products.get(1).getPrice(), "Second product price should be 49.99");
    }

}