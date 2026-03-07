package br.com.clean.wishlist.adapters.output.mongodb;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.clean.wishlist.adapters.output.persistence.mongodb.WishlistDocument;
import br.com.clean.wishlist.adapters.output.persistence.mongodb.WishlistDocumentMapper;
import br.com.clean.wishlist.adapters.output.persistence.mongodb.WishlistRepositoryMongoDB;
import br.com.clean.wishlist.adapters.output.persistence.mongodb.WishlistSpringDataMongoDB;
import br.com.clean.wishlist.domain.model.Wishlist;
import br.com.clean.wishlist.domain.vo.ProductId;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WishlistRepositoryMongoDBTest {

  @Mock private WishlistSpringDataMongoDB wishlistSpringDataMongoDB;
  @Mock private WishlistDocumentMapper wishlistDocumentMapper;
  @InjectMocks private WishlistRepositoryMongoDB repository;

  final String ID = "1";
  final String CUSTOMER_ID = "1";
  final Set<String> PRODUCTS_STRING = new HashSet<>(Set.of("100", "101", "102"));
  final Set<ProductId> PRODUCTS_PRODUCT_ID =
      new HashSet<>(Set.of(new ProductId("100"), new ProductId("101"), new ProductId("102")));

  @Test
  void findByCustomerId_WhenWishlistExist_ShouldReturnWishlist() {

    WishlistDocument document = new WishlistDocument(ID, CUSTOMER_ID, PRODUCTS_STRING);
    Wishlist wishlist = new Wishlist(ID, CUSTOMER_ID, PRODUCTS_PRODUCT_ID);

    when(wishlistSpringDataMongoDB.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(document));
    when(wishlistDocumentMapper.toWishlist(document)).thenReturn(wishlist);

    Optional<Wishlist> result = repository.findByCustomerId(CUSTOMER_ID);

    assertTrue(result.isPresent());
    assertThat(CUSTOMER_ID).isEqualTo(result.get().getCustomerId());

    verify(wishlistSpringDataMongoDB).findByCustomerId(CUSTOMER_ID);
    verify(wishlistDocumentMapper).toWishlist(document);
  }

  @Test
  void findByCustomerId_WhenWishlistExist_ShouldReturnOptionEmpty() {

    when(wishlistSpringDataMongoDB.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());

    Optional<Wishlist> result = repository.findByCustomerId(CUSTOMER_ID);

    assertTrue(result.isEmpty());
    verify(wishlistSpringDataMongoDB).findByCustomerId(CUSTOMER_ID);
  }

  @Test
  void save_WhenValidWishlist_ShouldSaveWishlist() {

    WishlistDocument document = new WishlistDocument(ID, CUSTOMER_ID, PRODUCTS_STRING);
    Wishlist wishlist = new Wishlist(ID, CUSTOMER_ID, PRODUCTS_PRODUCT_ID);

    when(wishlistDocumentMapper.toWishlistDocument(wishlist)).thenReturn(document);

    repository.save(wishlist);

    verify(wishlistDocumentMapper).toWishlistDocument(wishlist);
    verify(wishlistSpringDataMongoDB).save(document);
  }

  @Test
  void delete_WhenWishlistExist_ShouldDeleteWishlist() {

    WishlistDocument document = new WishlistDocument(ID, CUSTOMER_ID, PRODUCTS_STRING);
    Wishlist wishlist = new Wishlist(ID, CUSTOMER_ID, PRODUCTS_PRODUCT_ID);

    when(wishlistDocumentMapper.toWishlistDocument(wishlist)).thenReturn(document);

    repository.delete(wishlist);

    verify(wishlistDocumentMapper).toWishlistDocument(wishlist);
    verify(wishlistSpringDataMongoDB).delete(document);
  }
}
