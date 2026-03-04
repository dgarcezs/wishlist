package br.com.clean.wishlist.adapters.output.persistence.mongodb;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WishlistConfigurationSpringDataMongoDB extends
    MongoRepository<WishlistConfigurationDocument, String> {

  Optional<WishlistConfigurationDocument> findByKey(String key);
}
