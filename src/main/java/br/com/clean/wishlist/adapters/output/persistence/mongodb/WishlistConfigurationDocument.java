package br.com.clean.wishlist.adapters.output.persistence.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wishlist_configuration")
public class WishlistConfigurationDocument {

  @Id private String configKey;
  private String configValue;

  public WishlistConfigurationDocument(String configKey, String configValue) {
    this.configKey = configKey;
    this.configValue = configValue;
  }

  public String getConfigKey() {
    return configKey;
  }

  public void setConfigKey(String configKey) {
    this.configKey = configKey;
  }

  public String getConfigValue() {
    return configValue;
  }

  public void setConfigValue(String configValue) {
    this.configValue = configValue;
  }
}
