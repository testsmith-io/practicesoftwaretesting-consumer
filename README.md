### Product Listing Application

This is a Spring Boot application that displays a paginated list of products and rental products. The product list supports dynamic pagination using AJAX, allowing the product list to update without reloading the entire page.

### How to Run the Application

1. **Build the project:**
   ```bash
   mvn clean install
   ```

2. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

3. **Access the application:**
    - Open your browser and go to `http://localhost:8080` to see the product and rental listings.

### Running Tests

To run the consumer tests (Pact tests) against different versions of the contract:

1. **Switch the contract version:**
    - In `src/test/java/io/testsmith/productlisting/PactConsumerTest.java`, you can switch between:
        - `v4`
        - `v5`
        - `v5-with-bugs`

2. **Run the tests:**
   ```bash
   mvn test
   ```

### Features

- **Product Listing with Pagination**: Dynamically loads product pages using AJAX.
- **Rental Product Listing**: Displays a static list of all rental products.
- **Pact Consumer Tests**: Tests the API contracts for different product API versions.

