package io.testsmith.practicesoftwaretesting_consumer.productlisting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Category(
        String id,
        String name,
        String slug,
        @JsonProperty("parent_id") String parentId
) {}