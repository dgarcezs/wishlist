package br.com.clean.wishlist.bdd;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.clean.wishlist.application.wishlist.dto.WishlistResponseDTO;
import br.com.clean.wishlist.application.wishlist.service.WishlistService;
import br.com.clean.wishlist.application.wishlist.service.core.DefaultWishlistService;
import br.com.clean.wishlist.domain.exception.NotFoundException;
import br.com.clean.wishlist.domain.model.Wishlist;
import br.com.clean.wishlist.domain.repository.WishlistRepository;
import br.com.clean.wishlist.domain.vo.ProductId;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WishlistSteps {

  private final WishlistRepository wishlistRepository = mock(WishlistRepository.class);
  private final WishlistService wishlistService =
      new DefaultWishlistService(wishlistRepository, 10);

  private Set<ProductId> productIdSet = new HashSet<>();
  private String customerId;
  private String productId;

  @Given("the wishlist is empty for the customer {string}")
  public void the_wishlist_is_empty_for_the_customer(String customerId) {
    this.customerId = customerId;
    this.productIdSet.clear();
    when(wishlistRepository.findByCustomerId(this.customerId)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> wishlistService.getWishlist(this.customerId));
  }

  @When("the customer {string} adds a product {string} to the wishlist")
  public void the_customer_adds_a_product_to_the_wishlist(String customerId, String productId) {
    this.customerId = customerId;
    this.productId = productId;
    when(wishlistRepository.findByCustomerId(this.customerId))
        .thenReturn(Optional.of(new Wishlist("1", this.customerId, this.productIdSet)));
    wishlistService.addProduct(this.customerId, this.productId);
    this.productIdSet.add(new ProductId(this.productId));
  }

  @Then("the wishlist of a customer {string} should contain a product {string}")
  public void the_wishlist_of_a_customer_should_contain_product(
      String customerId, String productId) {
    this.customerId = customerId;
    this.productId = productId;

    WishlistResponseDTO wishlistResponseDTO = wishlistService.getWishlist(this.customerId);
    assertTrue(wishlistResponseDTO.getProducts().contains(this.productId));
  }

  @When("the customer {string} removes a product {string} from the wishlist")
  public void the_customer_removes_a_product_from_the_wishlist(
      String customerId, String productId) {
    this.customerId = customerId;
    this.productId = productId;

    wishlistService.removeProduct(this.customerId, this.productId);
    this.productIdSet.remove(new ProductId(this.productId));
  }

  @Then("the wishlist of a customer {string} should not contain a product {string}")
  public void the_wishlist_of_a_customer_should_not_contain_a_product(
      String customerId, String productId) {
    this.customerId = customerId;
    this.productId = productId;

    WishlistResponseDTO wishlistResponseDTO = wishlistService.getWishlist(this.customerId);
    assertFalse(wishlistResponseDTO.getProducts().contains(this.productId));
  }
}
