package br.com.clean.wishlist.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.clean.wishlist.domain.vo.ProductId;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WishlistTest {

  private Wishlist wishlist;
  private String id;
  private String customerId;

  @BeforeEach
  void setUp() {
    id = "wishlist-123";
    customerId = "customer-456";
    Set<ProductId> products = new HashSet<>();
    products.add(new ProductId("product-1"));
    products.add(new ProductId("product-2"));

    wishlist = new Wishlist(id, customerId, products);
  }

  @Test
  void constructor_WithAllParameters_ShouldCreateWishlist() {
    assertThat(wishlist.getId()).isEqualTo(id);
    assertThat(wishlist.getCustomerId()).isEqualTo(customerId);
    assertThat(wishlist.getProducts()).hasSize(2);
    assertThat(wishlist.getProducts()).contains(new ProductId("product-1"));
    assertThat(wishlist.getProducts()).contains(new ProductId("product-2"));
  }

  @Test
  void defaultConstructor_ShouldCreateEmptyWishlist() {
    Wishlist emptyWishlist = new Wishlist();

    assertThat(emptyWishlist.getId()).isNull();
    assertThat(emptyWishlist.getCustomerId()).isNull();
    assertThat(emptyWishlist.getProducts()).isNotNull();
    assertThat(emptyWishlist.getProducts()).isEmpty();
  }

  @Test
  void setId_ShouldUpdateId() {

    String newId = "new-wishlist-789";

    wishlist.setId(newId);

    assertThat(wishlist.getId()).isEqualTo(newId);
  }

  @Test
  void setCustomerId_ShouldUpdateCustomerId() {

    String newCustomerId = "new-customer-999";

    wishlist.setCustomerId(newCustomerId);

    assertThat(wishlist.getCustomerId()).isEqualTo(newCustomerId);
  }

  @Test
  void getProducts_ShouldReturnProductsSet() {

    Set<ProductId> retrievedProducts = wishlist.getProducts();

    assertThat(retrievedProducts).hasSize(2);
    assertThat(retrievedProducts).contains(new ProductId("product-1"));
    assertThat(retrievedProducts).contains(new ProductId("product-2"));

    int originalSize = retrievedProducts.size();
    retrievedProducts.add(new ProductId("product-3"));
    assertThat(retrievedProducts).hasSize(originalSize + 1);
  }

  @Test
  void isQtyProductsLimitReached_WhenLimitNotReached_ShouldReturnFalse() {

    int limit = 5;

    boolean result = wishlist.isQtyProductsLimitReached(limit);

    assertThat(result).isFalse();
  }

  @Test
  void isQtyProductsLimitReached_WhenLimitReached_ShouldReturnTrue() {

    int limit = 2;

    boolean result = wishlist.isQtyProductsLimitReached(limit);

    assertThat(result).isTrue();
  }

  @Test
  void isQtyProductsLimitReached_WhenLimitExceeded_ShouldReturnTrue() {

    int limit = 1;

    boolean result = wishlist.isQtyProductsLimitReached(limit);

    assertThat(result).isTrue();
  }

  @Test
  void isProductAlreadyInWishlist_WhenProductExists_ShouldReturnTrue() {

    ProductId existingProduct = new ProductId("product-1");

    boolean result = wishlist.isProductAlreadyInWishlist(existingProduct);

    assertThat(result).isTrue();
  }

  @Test
  void isProductAlreadyInWishlist_WhenProductDoesNotExist_ShouldReturnFalse() {

    ProductId nonExistingProduct = new ProductId("product-999");

    boolean result = wishlist.isProductAlreadyInWishlist(nonExistingProduct);

    assertThat(result).isFalse();
  }

  @Test
  void isProductAlreadyInWishlist_WithEmptyWishlist_ShouldReturnFalse() {

    Wishlist emptyWishlist = new Wishlist();
    ProductId product = new ProductId("product-1");

    boolean result = emptyWishlist.isProductAlreadyInWishlist(product);

    assertThat(result).isFalse();
  }
}
