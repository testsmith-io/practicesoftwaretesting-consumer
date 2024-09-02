package io.testsmith.practicesoftwaretesting_consumer.productlisting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductResponse {

    private int currentPage;
    private List<Product> data;
    private int from;
    private int lastPage;
    private int perPage;
    private int to;
    private int total;

    @JsonProperty("current_page")
    public int getCurrentPage() {
        return currentPage;
    }

    @JsonProperty("current_page")
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    @JsonProperty("last_page")
    public int getLastPage() {
        return lastPage;
    }

    @JsonProperty("last_page")
    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    @JsonProperty("per_page")
    public int getPerPage() {
        return perPage;
    }

    @JsonProperty("per_page")
    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}