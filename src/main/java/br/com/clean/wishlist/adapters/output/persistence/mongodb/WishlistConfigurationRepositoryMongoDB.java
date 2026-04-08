package br.com.clean.wishlist.adapters.output.persistence.mongodb;

import br.com.clean.wishlist.domain.repository.WishlistConfigurationRepository;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "wishlist.repository.type", havingValue = "mongodb")
public class WishlistConfigurationRepositoryMongoDB implements WishlistConfigurationRepository {

  private static final Logger LOGGER =
      LogManager.getLogger(WishlistConfigurationRepositoryMongoDB.class);

  private final WishlistConfigurationSpringDataMongoDB wishlistConfigurationSpringDataMongoDB;

  public WishlistConfigurationRepositoryMongoDB(
      WishlistConfigurationSpringDataMongoDB wishlistConfigurationSpringDataMongoDB) {
    this.wishlistConfigurationSpringDataMongoDB = wishlistConfigurationSpringDataMongoDB;
  }

  @Override
  public Optional<String> findValueByKey(String key) {
    return wishlistConfigurationSpringDataMongoDB
        .findByConfigKey(key)
        .map(WishlistConfigurationDocument::getConfigValue);
  }
}
