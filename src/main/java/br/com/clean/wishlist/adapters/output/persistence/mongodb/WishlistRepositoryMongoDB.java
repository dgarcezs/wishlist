package br.com.clean.wishlist.adapters.output.persistence.mongodb;

import br.com.clean.wishlist.domain.model.Wishlist;
import br.com.clean.wishlist.domain.repository.WishlistRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistRepositoryMongoDB implements WishlistRepository {

  private final WishlistSpringDataMongoDB wishlistSpringDataMongoDB;
  private final WishlistDocumentMapper wishlistDocumentMapper;

  public WishlistRepositoryMongoDB(WishlistSpringDataMongoDB wishlistSpringDataMongoDB,
      WishlistDocumentMapper wishlistDocumentMapper) {
    this.wishlistSpringDataMongoDB = wishlistSpringDataMongoDB;
    this.wishlistDocumentMapper = wishlistDocumentMapper;
  }

  @Override
  public Optional<Wishlist> findByCustomerId(String customerId) {
    return wishlistSpringDataMongoDB.findByCustomerId(customerId)
        .map(wishlistDocumentMapper::toWishlist);
  }

  @Override
  public void save(Wishlist wishlist) {
    WishlistDocument wishlistDocument = wishlistDocumentMapper.toWishlistDocument(wishlist);
    wishlistSpringDataMongoDB.save(wishlistDocument);
  }
}
