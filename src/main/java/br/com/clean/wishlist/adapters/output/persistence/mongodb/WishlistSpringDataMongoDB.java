package br.com.clean.wishlist.adapters.output.persistence.mongodb;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WishlistSpringDataMongoDB extends MongoRepository<WishlistDocument, String> {

  Optional<WishlistDocument> findByCustomerId(String customerId);
}
