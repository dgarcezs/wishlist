package br.com.clean.wishlist.adapters.output.persistence.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.AccessType.Type;

@Entity
@Table(name = "config_properties")
@AccessType(Type.FIELD)
public class WishlistConfigurationEntityPostgres {

  @Id
  @Column(name = "config_key", nullable = false)
  private String configKey;

  @Column(name = "config_value", nullable = false)
  private String configValue;

  public String getConfigKey() {
    return configKey;
  }

  public void setConfigKey(String configKey) {
    this.configKey = configKey;
  }
}
