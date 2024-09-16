package io.testsmith.practicesoftwaretesting_consumer.productlisting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductImage(
        String id,
        @JsonProperty("by_name") String byName,
        @JsonProperty("by_url") String byUrl,
        @JsonProperty("source_name") String sourceName,
        @JsonProperty("source_url") String sourceUrl,
        @JsonProperty("file_name") String fileName,
        String title
) {}