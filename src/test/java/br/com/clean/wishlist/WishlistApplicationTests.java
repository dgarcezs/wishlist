package br.com.clean.wishlist;

import br.com.clean.wishlist.adapters.output.persistence.mongodb.WishlistConfigurationSpringDataMongoDB;
import br.com.clean.wishlist.adapters.output.persistence.mongodb.WishlistSpringDataMongoDB;
import br.com.clean.wishlist.domain.repository.WishlistRepository;
import br.com.clean.wishlist.infrastructure.config.WishlistConfigurationProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.data.mongodb.autoconfigure.DataMongoAutoConfiguration;
import org.springframework.boot.mongodb.autoconfigure.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
@ImportAutoConfiguration(exclude = {MongoAutoConfiguration.class, DataMongoAutoConfiguration.class})
class WishlistApplicationTests {

  @MockitoBean private WishlistRepository wishlistRepository;
  @MockitoBean private WishlistConfigurationProvider wishlistConfigurationProvider;
  @MockitoBean private WishlistSpringDataMongoDB wishlistSpringDataMongoDB;

  @MockitoBean
  private WishlistConfigurationSpringDataMongoDB wishlistConfigurationSpringDataMongoDB;

  @Test
  void contextLoads() {}
}
