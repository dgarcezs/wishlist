package br.com.clean.wishlist.adapters.output.persistence.mongodb;

import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wishlists")
public class WishlistDocument {

  @Id
  private String id;
  private String customerId;
  private Set<String> products;

  public WishlistDocument(String id, String customerId, Set<String> products) {
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

  public Set<String> getProducts() {
    return products;
  }

  public void setProducts(Set<String> products) {
    this.products = products;
  }

}
