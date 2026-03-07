package br.com.clean.wishlist.domain.model;

import br.com.clean.wishlist.domain.vo.ProductId;
import java.util.HashSet;
import java.util.Set;

public class Wishlist {

  private String id;
  private String customerId;
  private Set<ProductId> products = new HashSet<>();

  public Wishlist() {}

  public Wishlist(String id, String customerId, Set<ProductId> products) {
    this.id = id;
    this.customerId = customerId;
    this.products = products;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public Set<ProductId> getProducts() {
    return products;
  }

  public boolean isQtyProductsLimitReached(int makProductsPerWishlist) {
    return products.size() >= makProductsPerWishlist;
  }

  public boolean isProductAlreadyInWishlist(ProductId productId) {
    return products.contains(productId);
  }
}
