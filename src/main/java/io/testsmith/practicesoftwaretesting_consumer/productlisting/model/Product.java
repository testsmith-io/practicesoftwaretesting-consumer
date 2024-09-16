package io.testsmith.practicesoftwaretesting_consumer.productlisting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Product(
        String id,
        String name,
        String description,
        double price,
        @JsonProperty("is_location_offer") boolean locationOffer,
        @JsonProperty("is_rental") boolean rental,
        @JsonProperty("in_stock") boolean inStock,
        Brand brand,
        Category category,
        @JsonProperty("product_image") ProductImage productImage
) {}