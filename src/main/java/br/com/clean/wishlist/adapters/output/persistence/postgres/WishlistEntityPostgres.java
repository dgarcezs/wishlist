package br.com.clean.wishlist.adapters.output.persistence.postgres;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.Set;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.AccessType.Type;

@Entity
@Table(name = "wishlist")
@AccessType(Type.FIELD)
public class WishlistEntityPostgres {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "customer_id", nullable = false)
  private String customerId;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "wishlist_product", joinColumns = @JoinColumn(name = "wishlist_id"))
  @Column(name = "product_id")
  private Set<String> products;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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
