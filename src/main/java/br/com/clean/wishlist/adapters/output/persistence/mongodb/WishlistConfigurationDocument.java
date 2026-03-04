package br.com.clean.wishlist.adapters.output.persistence.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wishlist_configuration")
public class WishlistConfigurationDocument {

  @Id
  private String key;
  private String value;

  public WishlistConfigurationDocument(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
