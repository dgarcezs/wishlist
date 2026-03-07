package br.com.clean.wishlist.domain.vo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.clean.wishlist.application.wishlist.exception.BusinessException;
import org.junit.jupiter.api.Test;

class ProductIdTest {

  @Test
  void constructor_WhenValidProductId_ShouldReturnProductId() {
    ProductId productId = new ProductId("100");
    assertThat(productId).isEqualTo(new ProductId("100"));
  }

  @Test
  void constructor_WhenNullProductId_ShouldThrowException() {
    BusinessException businessException =
        assertThrows(BusinessException.class, () -> new ProductId(null));
    assertThat(businessException.getMessage()).isEqualTo("Product ID cannot be null or blank");
  }

  @Test
  void constructor_WhenBlankProductId_ShouldThrowException() {
    BusinessException businessException =
        assertThrows(BusinessException.class, () -> new ProductId(""));
    assertThat(businessException.getMessage()).isEqualTo("Product ID cannot be null or blank");
  }

  @Test
  void constructor_WhenSpacesProductId_ShouldThrowException() {
    BusinessException businessException =
        assertThrows(BusinessException.class, () -> new ProductId("   "));
    assertThat(businessException.getMessage()).isEqualTo("Product ID cannot be null or blank");
  }

  @Test
  void equals_WhenSameObject_ShouldReturnTrue() {
    ProductId productId = new ProductId("100");
    assertThat(productId.equals(productId)).isTrue();
  }

  @Test
  void equals_WhenEqualObjects_ShouldReturnTrue() {
    ProductId productId1 = new ProductId("100");
    ProductId productId2 = new ProductId("100");
    assertThat(productId1.equals(productId2)).isTrue();
  }

  @Test
  void equals_WhenDifferentObjects_ShouldReturnFalse() {
    ProductId productId1 = new ProductId("100");
    ProductId productId2 = new ProductId("200");
    assertThat(productId1.equals(productId2)).isFalse();
  }

  @Test
  void equals_WhenNullObject_ShouldReturnFalse() {
    ProductId productId = new ProductId("100");
    assertThat(productId.equals(null)).isFalse();
  }

  @Test
  void equals_WhenDifferentClass_ShouldReturnFalse() {
    ProductId productId = new ProductId("100");
    assertThat(productId.equals("100")).isFalse();
  }

  @Test
  void equals_WhenDifferentHashCodeButSameValue_ShouldReturnTrue() {
    ProductId productId1 = new ProductId("100");
    ProductId productId2 = new ProductId("100");
    assertThat(productId1.hashCode()).isEqualTo(productId2.hashCode());
    assertThat(productId1.equals(productId2)).isTrue();
  }
}
