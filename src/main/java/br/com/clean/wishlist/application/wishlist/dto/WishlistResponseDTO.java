package br.com.clean.wishlist.application.wishlist.dto;

import java.util.Set;

public class WishlistResponseDTO {
  private String customerId;
  private Set<String> products;

  public WishlistResponseDTO(String customerId, Set<String> products) {
    this.customerId = customerId;
    this.products = products;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public Set<String> getProducts() {
    return products;
  }

  public void setProducts(Set<String> products) {
    this.products = products;
  }
}
