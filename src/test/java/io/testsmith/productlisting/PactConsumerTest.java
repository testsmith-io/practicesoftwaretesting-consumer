package io.testsmith.productlisting;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactDirectory;
import io.testsmith.practicesoftwaretesting_consumer.PracticesoftwaretestingConsumerApplication;
import io.testsmith.practicesoftwaretesting_consumer.productlisting.model.Product;
import io.testsmith.practicesoftwaretesting_consumer.productlisting.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = PracticesoftwaretestingConsumerApplication.class)
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ProductProvider", port = "8081")
public class PactConsumerTest {

	@Autowired
	private ProductService productService;

	@DynamicPropertySource
	static void dynamicProperties(DynamicPropertyRegistry registry) {
		registry.add("api.baseUrl", () -> "http://localhost:8081");
	}

	@Pact(provider = "ProductProvider", consumer = "ProductConsumer")
	public V4Pact createPact(PactBuilder builder) {
		return builder
				.usingLegacyDsl()  // Use legacy DSL if needed
				.given("A request for all products with is_rental=true")
				.uponReceiving("A request for all products with is_rental=true")
				.path("/products")  // Ensure this matches the exact path expected
				.method("GET")
				.willRespondWith()
				.status(200)
				.headers(Map.of("Content-Type", "application/json"))
				.body("{\"current_page\":1,\"data\":[{\"id\":\"01J6Q1KDC8P3CPWRZYAYGNYC5J\",\"name\":\"Combination Pliers....\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris viverra felis nec pellentesque feugiat. Donec faucibus arcu maximus, convallis nisl eu, placerat dolor. Morbi finibus neque nec tincidunt pharetra. Sed eget tortor malesuada, mollis enim id, condimentum nisi. In viverra quam at bibendum ultricies. Aliquam quis eros ex. Etiam at pretium massa, ut pharetra tortor. Sed vel metus sem. Suspendisse ac molestie turpis. Duis luctus justo massa, faucibus ornare eros elementum et. Vestibulum quis nisl vitae ante dapibus tempor auctor ut leo. Mauris consectetur et magna at ultricies. Proin a aliquet turpis.\",\"price\":14.15,\"is_location_offer\":false,\"is_rental\":false,\"in_stock\":true,\"product_image\":{\"id\":\"01J6Q1KDC13YKGKGK728DDAGS7\",\"by_name\":\"Helinton Fantin\",\"by_url\":\"https://unsplash.com/@fantin\",\"source_name\":\"Unsplash\",\"source_url\":\"https://unsplash.com/photos/W8BNwvOvW4M\",\"file_name\":\"pliers01.avif\",\"title\":\"Combination pliers\"},\"category\":{\"id\":\"01J6Q1KDBR9XAWPSB1MFDDRVJJ\",\"name\":\"Pliers\",\"slug\":\"pliers\",\"parent_id\":\"01J6Q1KDBMMGN28DB8TDM99BR8\"},\"brand\":{\"id\":\"01J6Q1KDBBQ3KBHGNRZWQZSFJ7\",\"name\":\"ForgeFlex Tools\",\"slug\":\"forgeflex-tools\"}},{\"id\":\"01J6Q1KDCAP5EQTQHGWFRRWHJS\",\"name\":\"Pliers\",\"description\":\"Nunc vulputate, orci at congue faucibus, enim neque sodales nulla, nec imperdiet augue odio vel nibh. Etiam auctor, ligula quis gravida dictum, mi massa commodo ante, sollicitudin pulvinar nulla justo hendrerit lacus. Vivamus rutrum pharetra molestie. Fusce tristique odio tristique, elementum est eget, porttitor diam. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Maecenas at ligula sed sapien porta pretium. Aenean cursus, magna in blandit consectetur, libero orci aliquet eros, et maximus nunc est eu dolor. Aenean non pellentesque eros. In sodales orci eget orci fringilla, vitae feugiat elit porta. Etiam aliquam, mi pretium tempus mattis, mauris ipsum gravida risus, at tempor nulla ipsum molestie ligula. Ut placerat, urna sit amet tincidunt volutpat, ex orci luctus purus, nec laoreet dolor sapien vel erat.\",\"price\":12.01,\"is_location_offer\":false,\"is_rental\":false,\"in_stock\":true,\"product_image\":{\"id\":\"01J6Q1KDC13YKGKGK728DDAGS8\",\"by_name\":\"Everyday basics\",\"by_url\":\"https://unsplash.com/@zanardi\",\"source_name\":\"Unsplash\",\"source_url\":\"https://unsplash.com/photos/I8eTuMmxIfo\",\"file_name\":\"pliers02.avif\",\"title\":\"Pliers\"},\"category\":{\"id\":\"01J6Q1KDBR9XAWPSB1MFDDRVJJ\",\"name\":\"Pliers\",\"slug\":\"pliers\",\"parent_id\":\"01J6Q1KDBMMGN28DB8TDM99BR8\"},\"brand\":{\"id\":\"01J6Q1KDBBQ3KBHGNRZWQZSFJ7\",\"name\":\"ForgeFlex Tools\",\"slug\":\"forgeflex-tools\"}}],\"from\":1,\"last_page\":5,\"per_page\":9,\"to\":9,\"total\":45}")
				.toPact(V4Pact.class);

	}

	@Test
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