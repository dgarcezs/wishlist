package br.com.clean.wishlist.domain.repository;

import br.com.clean.wishlist.domain.model.Wishlist;
import java.util.Optional;

public interface WishlistRepository {

  Optional<Wishlist> findByCustomerId(String customerId);

  void save(Wishlist wishlist);

}
