package br.com.clean.wishlist.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import br.com.clean.wishlist.WishlistApplication;
import br.com.clean.wishlist.adapters.input.dto.RestResponseDTO;
import br.com.clean.wishlist.adapters.output.persistence.mongodb.WishlistConfigurationSpringDataMongoDB;
import br.com.clean.wishlist.adapters.output.persistence.mongodb.WishlistSpringDataMongoDB;
import br.com.clean.wishlist.application.wishlist.dto.WishlistResponseDTO;
import br.com.clean.wishlist.config.WishlistTestConfig;
import br.com.clean.wishlist.domain.model.Wishlist;
import br.com.clean.wishlist.domain.repository.WishlistRepository;
import br.com.clean.wishlist.domain.vo.ProductId;
import br.com.clean.wishlist.infrastructure.config.WishlistConfigurationProvider;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.data.mongodb.autoconfigure.DataMongoAutoConfiguration;
import org.springframework.boot.mongodb.autoconfigure.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestClient;

@SpringBootTest(
    classes = {WishlistApplication.class, WishlistTestConfig.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles("test")
@ImportAutoConfiguration(exclude = {MongoAutoConfiguration.class, DataMongoAutoConfiguration.class})
class WishlistIntegrationTest {

  @LocalServerPort private int port;

  @MockitoBean private WishlistSpringDataMongoDB wishlistSpringDataMongoDB;

  @MockitoBean
  private WishlistConfigurationSpringDataMongoDB wishlistConfigurationSpringDataMongoDB;

  @MockitoBean private WishlistRepository wishlistRepository;

  @Autowired private WishlistConfigurationProvider wishlistConfigurationProvider;

  private RestClient restClient() {
    return RestClient.builder().baseUrl(String.format("http://localhost:%d", port)).build();
  }

  @Test
  void addAndGetProductInWishlist() {
    String wishlistId = "1";
    String customerId = "1";
    String productId = "100";
    Wishlist wishlist = new Wishlist(wishlistId, customerId, new HashSet<>());

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(3);
    when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

    restClient()
        .post()
        .uri(String.format("/api/wishlists/%s/products/%s", customerId, productId))
        .contentType(MediaType.APPLICATION_JSON)
        .retrieve()
        .toBodilessEntity();

    wishlist = new Wishlist(wishlistId, customerId, Set.of(new ProductId(productId)));
    when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

    ResponseEntity<RestResponseDTO<WishlistResponseDTO>> response =
        restClient()
            .get()
            .uri(String.format("/api/wishlists/%s", customerId))
            .retrieve()
            .toEntity(new ParameterizedTypeReference<RestResponseDTO<WishlistResponseDTO>>() {});

    assertNotNull(response.getBody());

    WishlistResponseDTO wishlistResponseDTO = response.getBody().getData();
    assertNotNull(wishlistResponseDTO.getProducts());
    assertTrue(wishlistResponseDTO.getProducts().contains(productId));
  }
}
