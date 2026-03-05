package br.com.clean.wishlist.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.clean.wishlist.application.wishlist.config.WishlistConfigurationProvider;
import br.com.clean.wishlist.application.wishlist.dto.WishlistResponseDTO;
import br.com.clean.wishlist.application.wishlist.exception.NotFoundException;
import br.com.clean.wishlist.application.wishlist.exception.ValidationException;
import br.com.clean.wishlist.application.wishlist.usecase.WishlistUseCase;
import br.com.clean.wishlist.application.wishlist.validation.WishlistErrors;
import br.com.clean.wishlist.domain.model.Wishlist;
import br.com.clean.wishlist.domain.repository.WishlistRepository;
import br.com.clean.wishlist.domain.vo.ProductId;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class WishlistUseCaseTest {

  private WishlistRepository wishlistRepository;
  private WishlistConfigurationProvider wishlistConfigurationProvider;
  private WishlistUseCase wishlistUseCase;

  private final String ID = "id_1";
  private final String CUSTOMER_ID = "1";
  private final String OTHER_CUSTOMER_ID = "2";
  private final String PRODUCT_ID_100 = "100";
  private final String PRODUCT_ID_101 = "101";
  private final String PRODUCT_ID_102 = "102";

  @BeforeEach
  void setUp() {
    wishlistRepository = mock(WishlistRepository.class);
    wishlistConfigurationProvider = mock(WishlistConfigurationProvider.class);
  }

  @Test
  void getWishlist_WhenCustomerIdIsValid_ShouldReturnWishlistResponseDTO() {

    Wishlist wishlist =
        new Wishlist(
            ID,
            CUSTOMER_ID,
            new HashSet<>(
                Set.of(
                    new ProductId(PRODUCT_ID_100),
                    new ProductId(PRODUCT_ID_101),
                    new ProductId(PRODUCT_ID_102))));

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(6);
    when(wishlistRepository.findByCustomerId(any())).thenReturn(Optional.of(wishlist));

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    WishlistResponseDTO wishlistResponseDTO = wishlistUseCase.getWishlist(CUSTOMER_ID);

    assertThat(wishlistResponseDTO).isNotNull();
    assertThat(wishlistResponseDTO.getCustomerId()).isEqualTo(CUSTOMER_ID);
    assertThat(wishlistResponseDTO.getProducts())
        .hasSize(3)
        .contains(PRODUCT_ID_100, PRODUCT_ID_101, PRODUCT_ID_102);
  }

  @ParameterizedTest
  @MethodSource("invalidCustomerIdInputParameter")
  void getWishlist_WhenInvalidCustomerIdProvided_ShouldThrowException(
      String customerId, String expectedMessage) {

    Wishlist wishlist =
        new Wishlist(
            ID,
            CUSTOMER_ID,
            new HashSet<>(
                Set.of(
                    new ProductId(PRODUCT_ID_100),
                    new ProductId(PRODUCT_ID_101),
                    new ProductId(PRODUCT_ID_102))));

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(6);
    when(wishlistRepository.findByCustomerId(any())).thenReturn(Optional.of(wishlist));

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> wishlistUseCase.getWishlist(customerId));

    assertThat(exception.getMessage()).isEqualTo(expectedMessage);
  }

  @Test
  void getWishlist_WhenWishlistDoesNotExists_ShouldThrowException() {

    Wishlist wishlist =
        new Wishlist(
            ID,
            CUSTOMER_ID,
            new HashSet<>(
                Set.of(
                    new ProductId(PRODUCT_ID_100),
                    new ProductId(PRODUCT_ID_101),
                    new ProductId(PRODUCT_ID_102))));

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(6);
    when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(wishlist));

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    NotFoundException exception =
        assertThrows(NotFoundException.class, () -> wishlistUseCase.getWishlist(OTHER_CUSTOMER_ID));

    assertThat(exception.getMessage()).isEqualTo("Wishlist not found.");
  }

  @Test
  void addProduct_WhenValidParameters_ShouldAddProductToWishlist() {

    HashSet<ProductId> products = new HashSet<>();
    Wishlist wishlist = new Wishlist(ID, CUSTOMER_ID, products);

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(6);
    when(wishlistRepository.findByCustomerId(any())).thenReturn(Optional.of(wishlist));

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    wishlistUseCase.addProduct(CUSTOMER_ID, PRODUCT_ID_100);

    assertTrue(products.contains(new ProductId(PRODUCT_ID_100)));
    verify(wishlistRepository).save(wishlist);
  }

  @ParameterizedTest
  @MethodSource("invalidCustomerIdOrProductInputParameters")
  void addProduct_WhenWithInvalidParameters_ShouldThrowException(
      String customerId, String productId, String expectedMessage) {

    HashSet<ProductId> products = new HashSet<>();
    Wishlist wishlist = new Wishlist(ID, CUSTOMER_ID, products);

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(6);
    when(wishlistRepository.findByCustomerId(any())).thenReturn(Optional.of(wishlist));

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> wishlistUseCase.addProduct(customerId, productId));
    assertThat(expectedMessage).isEqualTo(exception.getMessage());
  }

  @Test
  void addProduct_WhenWishlistDoesNotExist_ShouldCreateANewWishlistAndAddProduct() {

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(6);
    when(wishlistRepository.findByCustomerId(any())).thenReturn(Optional.empty());

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    wishlistUseCase.addProduct(CUSTOMER_ID, PRODUCT_ID_100);

    verify(wishlistRepository)
        .save(
            argThat(
                wishlist ->
                    wishlist.getCustomerId().equals(CUSTOMER_ID)
                        && wishlist.getProducts().contains(new ProductId(PRODUCT_ID_100))));
  }

  @Test
  void addProduct_WhenProductAlreadyExists_ShouldThrowException() {

    Wishlist wishlist =
        new Wishlist(
            ID,
            CUSTOMER_ID,
            new HashSet<>(
                Set.of(
                    new ProductId(PRODUCT_ID_100),
                    new ProductId(PRODUCT_ID_101),
                    new ProductId(PRODUCT_ID_102))));

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(6);
    when(wishlistRepository.findByCustomerId(any())).thenReturn(Optional.of(wishlist));

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    ValidationException exception =
        assertThrows(
            ValidationException.class,
            () -> wishlistUseCase.addProduct(CUSTOMER_ID, PRODUCT_ID_100));
    assertTrue(exception.getValidationResults().contains(WishlistErrors.productIdAlreadyExists()));
  }

  @Test
  void addProduct_WhenWishListProductsLimitReached_ShouldThrowException() {

    int maxProductsPerWishlist = 2;

    Wishlist wishlist =
        new Wishlist(
            ID,
            CUSTOMER_ID,
            new HashSet<>(Set.of(new ProductId(PRODUCT_ID_100), new ProductId(PRODUCT_ID_101))));

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist())
        .thenReturn(maxProductsPerWishlist);
    when(wishlistRepository.findByCustomerId(any())).thenReturn(Optional.of(wishlist));

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    ValidationException exception =
        assertThrows(
            ValidationException.class,
            () -> wishlistUseCase.addProduct(CUSTOMER_ID, PRODUCT_ID_102));
    assertTrue(
        exception
            .getValidationResults()
            .contains(WishlistErrors.maxProductsPerWishlistReached(maxProductsPerWishlist)));
  }

  @Test
  void removeProduct_WhenValidParameters_ShouldRemoveProduct() {

    Wishlist wishlist =
        new Wishlist(
            ID,
            CUSTOMER_ID,
            new HashSet<>(
                Set.of(
                    new ProductId(PRODUCT_ID_100),
                    new ProductId(PRODUCT_ID_101),
                    new ProductId(PRODUCT_ID_102))));

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(6);
    when(wishlistRepository.findByCustomerId(any())).thenReturn(Optional.of(wishlist));

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    wishlistUseCase.removeProduct(CUSTOMER_ID, PRODUCT_ID_100);

    verify(wishlistRepository).save(wishlist);
  }

  @ParameterizedTest
  @MethodSource("invalidCustomerIdOrProductInputParameters")
  void removeProduct_WhenInvalidInputParameters_ShouldThrowException(
      String customerId, String productId, String expectedMessage) {

    Wishlist wishlist =
        new Wishlist(
            ID,
            CUSTOMER_ID,
            new HashSet<>(
                Set.of(
                    new ProductId(PRODUCT_ID_100),
                    new ProductId(PRODUCT_ID_101),
                    new ProductId(PRODUCT_ID_102))));

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(6);
    when(wishlistRepository.findByCustomerId(any())).thenReturn(Optional.of(wishlist));

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> wishlistUseCase.removeProduct(customerId, productId));
    assertThat(expectedMessage).isEqualTo(exception.getMessage());
  }

  @Test
  void removeProduct_WhenLastWishListProduct_ShouldDeleteWishList() {

    Wishlist wishlist =
        new Wishlist(ID, CUSTOMER_ID, new HashSet<>(Set.of(new ProductId(PRODUCT_ID_100))));

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(6);
    when(wishlistRepository.findByCustomerId(any())).thenReturn(Optional.of(wishlist));

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    wishlistUseCase.removeProduct(CUSTOMER_ID, PRODUCT_ID_100);

    verify(wishlistRepository).delete(wishlist);
  }

  @Test
  void removeProduct_WhenProductDoesNotExists_ShouldThrowException() {

    Wishlist wishlist =
        new Wishlist(
            ID,
            CUSTOMER_ID,
            new HashSet<>(Set.of(new ProductId(PRODUCT_ID_101), new ProductId(PRODUCT_ID_102))));

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(6);
    when(wishlistRepository.findByCustomerId(any())).thenReturn(Optional.of(wishlist));

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    NotFoundException exception =
        assertThrows(
            NotFoundException.class,
            () -> wishlistUseCase.removeProduct(CUSTOMER_ID, PRODUCT_ID_100));
    assertTrue(exception.getMessage().contains("Product not found in wishlist."));
  }

  @Test
  void removeProduct_WhenWishlistDoesNotExist_ShouldThrowException() {

    Wishlist wishlist =
        new Wishlist(
            ID,
            CUSTOMER_ID,
            new HashSet<>(Set.of(new ProductId(PRODUCT_ID_101), new ProductId(PRODUCT_ID_102))));

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(6);
    when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(wishlist));

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    NotFoundException exception =
        assertThrows(
            NotFoundException.class,
            () -> wishlistUseCase.removeProduct(OTHER_CUSTOMER_ID, PRODUCT_ID_100));
    assertTrue(exception.getMessage().contains("Wishlist not found."));
  }

  @Test
  void removeWish_WhenValidParameter_ShouldRemoveWishlist() {

    Wishlist wishlist =
        new Wishlist(ID, CUSTOMER_ID, new HashSet<>(Set.of(new ProductId(PRODUCT_ID_100))));

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(6);
    when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(wishlist));

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    wishlistUseCase.removeWishlist(CUSTOMER_ID);
    verify(wishlistRepository).delete(wishlist);
  }

  @ParameterizedTest
  @MethodSource("invalidCustomerIdInputParameter")
  void removeWishlist_WhenInvalidInputParameter_ShouldThrowException(
      String customerId, String expectedMessage) {

    Wishlist wishlist =
        new Wishlist(ID, CUSTOMER_ID, new HashSet<>(Set.of(new ProductId(PRODUCT_ID_100))));

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(6);
    when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(wishlist));

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class, () -> wishlistUseCase.removeWishlist(customerId));
    assertThat(expectedMessage).isEqualTo(exception.getMessage());
  }

  @Test
  void removeWishlist_WhenWishlistDoesNotExist_ShouldThrowException() {

    Wishlist wishlist =
        new Wishlist(ID, CUSTOMER_ID, new HashSet<>(Set.of(new ProductId(PRODUCT_ID_100))));

    when(wishlistConfigurationProvider.getMaxProductsPerWishlist()).thenReturn(6);
    when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(wishlist));

    wishlistUseCase =
        new WishlistUseCase(
            wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());

    NotFoundException exception =
        assertThrows(
            NotFoundException.class, () -> wishlistUseCase.removeWishlist(OTHER_CUSTOMER_ID));
    assertTrue(exception.getMessage().contains("Wishlist not found."));
  }

  private static Stream<Arguments> invalidCustomerIdOrProductInputParameters() {
    return Stream.of(
        Arguments.of(null, "101", "Customer ID cannot be null or empty"),
        Arguments.of("1", null, "Product ID cannot be null or empty"),
        Arguments.of("  ", "101", "Customer ID cannot be null or empty"),
        Arguments.of("1", "   ", "Product ID cannot be null or empty"),
        Arguments.of("", "101", "Customer ID cannot be null or empty"),
        Arguments.of("1", "", "Product ID cannot be null or empty"));
  }

  private static Stream<Arguments> invalidCustomerIdInputParameter() {
    return Stream.of(
        Arguments.of(null, "Customer ID cannot be null or empty"),
        Arguments.of("  ", "Customer ID cannot be null or empty"),
        Arguments.of("", "Customer ID cannot be null or empty"));
  }
}
