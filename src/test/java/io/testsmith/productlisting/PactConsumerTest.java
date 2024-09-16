package io.testsmith.productlisting;

import au.com.dius.pact.consumer.MockHttpServer;
import au.com.dius.pact.consumer.model.MockProviderConfig;
import au.com.dius.pact.core.model.BasePact;
import au.com.dius.pact.core.model.DefaultPactReader;
import io.testsmith.practicesoftwaretesting_consumer.PracticesoftwaretestingConsumerApplication;
import io.testsmith.practicesoftwaretesting_consumer.productlisting.model.Product;
import io.testsmith.practicesoftwaretesting_consumer.productlisting.service.ProductService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PracticesoftwaretestingConsumerApplication.class)
public class PactConsumerTest {

    private static MockHttpServer mockServer;

    @Autowired
    private ProductService productService;

    @BeforeAll
    public static void setup() {
        // URL to the Pact JSON file
        // Version 4
        // String pactUrl = "https://raw.githubusercontent.com/testsmith-io/practice-software-testing/main/sprint4/API/pacts/anyconsumer-productapi.json";
        // Version 5
        String pactUrl = "https://raw.githubusercontent.com/testsmith-io/practice-software-testing/main/sprint5/API/pacts/anyconsumer-productapi.json";
        // Version 5 - with bugs
        // String pactUrl = "https://raw.githubusercontent.com/testsmith-io/practice-software-testing/main/sprint5-with-bugs/API/pacts/anyconsumer-productapi.json";

        RestTemplate restTemplate = new RestTemplate();
        String pactJson = restTemplate.getForObject(pactUrl, String.class);

        BasePact pact = (BasePact) DefaultPactReader.INSTANCE.loadPact(pactJson);

        MockProviderConfig config = MockProviderConfig.createDefault();
        mockServer = new MockHttpServer(pact, config);
        mockServer.start();

        // Set the system property to point to the mock server
        System.setProperty("api.baseUrl", mockServer.getUrl());
    }

    @Test
    public void testGetProductsFromProvider() {
        List<Product> products = productService.getProducts();

        System.out.println(products);
        assertNotNull(products, "Products list should not be null");
        assertTrue(products.size() > 0, "Products list should not be empty");
        assertEquals(2, products.size(), "Product list size should be 2");
        assertEquals("Combination Pliers", products.get(0).name(), "First product name should be 'Combination Pliers'");
        assertEquals(true, products.get(0).inStock(), "First product should be in stock");
        assertEquals(14.15, products.get(0).price(), "First product price should be 14.15");
        assertEquals("Pliers", products.get(1).name(), "Second product name should be 'Pliers'");
        assertEquals(12.01, products.get(1).price(), "Second product price should be 12.01");
    }

    @AfterAll
    public static void teardown() {
        if (mockServer != null) {
            mockServer.stop();
        }
    }
}