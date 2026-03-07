package br.com.clean.wishlist.adapters.input.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.clean.wishlist.adapters.input.handler.GlobalExceptionHandler;
import br.com.clean.wishlist.application.wishlist.dto.WishlistResponseDTO;
import br.com.clean.wishlist.application.wishlist.usecase.WishlistUseCase;
import br.com.clean.wishlist.domain.exception.NotFoundException;
import br.com.clean.wishlist.domain.exception.ValidationException;
import br.com.clean.wishlist.domain.validation.ValidationResult;
import br.com.clean.wishlist.domain.validation.WishlistErrors;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class WishlistControllerTest {

  @Mock private WishlistUseCase wishlistUseCase;
  @InjectMocks private WishlistController wishlistController;

  private MockMvc mockMvc;

  private final String CUSTOMER_ID = "1";
  private final String PRODUCT_ID = "100";
  private final Set<String> PRODUCTS = Set.of("100", "101", "102");

  @BeforeEach
  void setUp() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(wishlistController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
  }

  @Test
  void getWishlist_WhenValidCustomerId_ShouldReturnWishlist() throws Exception {
    WishlistResponseDTO responseDTO = new WishlistResponseDTO(CUSTOMER_ID, PRODUCTS);
    when(wishlistUseCase.getWishlist(CUSTOMER_ID)).thenReturn(responseDTO);

    mockMvc
        .perform(get("/api/wishlists/{customerId}", CUSTOMER_ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.customerId").value(CUSTOMER_ID))
        .andExpect(jsonPath("$.data.products").isArray())
        .andExpect(jsonPath("$.message").value("Success"))
        .andExpect(jsonPath("$.status").value(200));
  }

  @Test
  void getWishlist_WhenWishlistNotFound_ShouldReturnNotFound() throws Exception {
    when(wishlistUseCase.getWishlist(CUSTOMER_ID))
        .thenThrow(new NotFoundException("Wishlist not found."));

    mockMvc
        .perform(get("/api/wishlists/{customerId}", CUSTOMER_ID))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("Not Found"))
        .andExpect(jsonPath("$.detail").value("Wishlist not found."))
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  void addProductToWishlist_WhenSuccess_ShouldReturnCreated() throws Exception {
    doNothing().when(wishlistUseCase).addProduct(CUSTOMER_ID, PRODUCT_ID);

    mockMvc
        .perform(post("/api/wishlists/{customerId}/products/{productId}", CUSTOMER_ID, PRODUCT_ID))
        .andExpect(status().isCreated());
  }

  @Test
  void addProductToWishlist_WhenValidationFails_ShouldReturnBadRequest() throws Exception {
    List<ValidationResult> validations = List.of(WishlistErrors.productIdAlreadyExists());

    doThrow(new ValidationException(validations))
        .when(wishlistUseCase)
        .addProduct(CUSTOMER_ID, PRODUCT_ID);

    mockMvc
        .perform(post("/api/wishlists/{customerId}/products/{productId}", CUSTOMER_ID, PRODUCT_ID))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("Validation failed"))
        .andExpect(jsonPath("$.detail").value("One or more validation errors occurred"))
        .andExpect(jsonPath("$.status").value(400));
  }

  @Test
  void removeProductFromWishlist_WhenSuccess_ShouldReturnNoContent() throws Exception {
    doNothing().when(wishlistUseCase).removeProduct(CUSTOMER_ID, PRODUCT_ID);

    mockMvc
        .perform(
            delete("/api/wishlists/{customerId}/products/{productId}", CUSTOMER_ID, PRODUCT_ID))
        .andExpect(status().isNoContent());
  }

  @Test
  void removeProductFromWishlist_WhenWishlistNotFound_ShouldReturnNotFound() throws Exception {
    doThrow(new NotFoundException("Wishlist not found."))
        .when(wishlistUseCase)
        .removeProduct(CUSTOMER_ID, PRODUCT_ID);

    mockMvc
        .perform(
            delete("/api/wishlists/{customerId}/products/{productId}", CUSTOMER_ID, PRODUCT_ID))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("Not Found"))
        .andExpect(jsonPath("$.detail").value("Wishlist not found."))
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  void removeProductFromWishlist_WhenProductNotFound_ShouldReturnNotFound() throws Exception {
    doThrow(new NotFoundException("Product not found in wishlist."))
        .when(wishlistUseCase)
        .removeProduct(CUSTOMER_ID, PRODUCT_ID);

    mockMvc
        .perform(
            delete("/api/wishlists/{customerId}/products/{productId}", CUSTOMER_ID, PRODUCT_ID))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("Not Found"))
        .andExpect(jsonPath("$.detail").value("Product not found in wishlist."))
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  void removeWishlist_WhenSuccess_ShouldReturnNoContent() throws Exception {
    doNothing().when(wishlistUseCase).removeWishlist(CUSTOMER_ID);

    mockMvc
        .perform(delete("/api/wishlists/{customerId}", CUSTOMER_ID))
        .andExpect(status().isNoContent());
  }
}
