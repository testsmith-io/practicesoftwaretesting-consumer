package io.testsmith.practicesoftwaretesting_consumer.productlisting.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.testsmith.practicesoftwaretesting_consumer.productlisting.model.Product;
import io.testsmith.practicesoftwaretesting_consumer.productlisting.model.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class RentalService {

    private final RestTemplate restTemplate;
    private final String apiBaseUrl;

    public RentalService(@Value("${api.baseUrl}") String apiBaseUrl) {
        this.restTemplate = createRestTemplate();
        this.apiBaseUrl = apiBaseUrl;
        System.out.println("Initialized ProductService with apiBaseUrl: " + apiBaseUrl);  // Debugging line
    }

    // Configure RestTemplate with a custom ObjectMapper that fails on unknown properties
    private RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true); // Strict mode

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper); // Set custom ObjectMapper to RestTemplate
        restTemplate.getMessageConverters().add(0, converter);

        return restTemplate;
    }

    public List<Product> getRentalProducts() {
        URI uri = UriComponentsBuilder.fromUriString(apiBaseUrl + "/products")
                .queryParam("is_rental", true)  // Add query parameter
                .build()
                .toUri();

        System.out.println("Requesting URL: " + uri);  // Debugging line
        ProductResponse productResponse = null;
        try {
            productResponse = restTemplate.getForObject(uri, ProductResponse.class);
            if (productResponse == null || productResponse.getData() == null) {
                return List.of(); // return empty list if no data
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch products due to deserialization error", e);
        }
        return productResponse.getData();
    }
}
