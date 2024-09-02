package io.testsmith.practicesoftwaretesting_consumer.productlisting.service;

import io.testsmith.practicesoftwaretesting_consumer.productlisting.model.Product;
import io.testsmith.practicesoftwaretesting_consumer.productlisting.model.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductService {
    private final RestTemplate restTemplate;
    private final String apiBaseUrl;

    public ProductService(@Value("${api.baseUrl}") String apiBaseUrl) {
        this.restTemplate = new RestTemplate();
        this.apiBaseUrl = apiBaseUrl;
        System.out.println("Initialized ProductService with apiBaseUrl: " + apiBaseUrl);  // Debugging line
    }

    public List<Product> getProducts() {
        String url = apiBaseUrl + "/products";
        System.out.println("Requesting URL: " + url);  // Debugging line
        ProductResponse productResponse = null;
        try {
            productResponse = restTemplate.getForObject(url, ProductResponse.class);
            if (productResponse == null || productResponse.getData() == null) {
                return List.of(); // return empty list if no data
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productResponse.getData();
    }
}