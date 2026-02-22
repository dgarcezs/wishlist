package br.com.clean.wishlist.domain.model;

import br.com.clean.wishlist.domain.vo.ProductId;
import java.util.HashSet;
import java.util.Set;

public class Wishlist {

  private String id;
  private String customerId;
  private final Set<ProductId> products = new HashSet<>();

  public Wishlist() {
  }

  public Wishlist(String id, String customerId) {
    this.id = id;
    this.customerId = customerId;
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


}
