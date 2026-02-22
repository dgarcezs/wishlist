package br.com.clean.wishlist.adapters.output.persistence.mongodb;

import br.com.clean.wishlist.domain.model.Wishlist;
import br.com.clean.wishlist.domain.vo.ProductId;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WishlistDocumentMapper {

  WishlistDocumentMapper INSTANCE = Mappers.getMapper(WishlistDocumentMapper.class);

  @Mapping(target = "products", source = "products", qualifiedByName = "toProductStrings")
  WishlistDocument toWishlistDocument(Wishlist wishlist);

  @Mapping(target = "products", source = "products", qualifiedByName = "toProductIds")
  Wishlist toWishlist(WishlistDocument wishlistDocument);

  @Named("toProductStrings")
  default Set<String> toProductStrings(Set<ProductId> productIds) {
    if (productIds == null) {
      return null;
    }
    return productIds.stream()
        .map(ProductId::value)
        .collect(Collectors.toSet());
  }

  @Named("toProductIds")
  default Set<ProductId> toProductIds(Set<String> productStrings) {
    if (productStrings == null) {
      return null;
    }
    return productStrings.stream()
        .map(ProductId::new)
        .collect(Collectors.toSet());
  }
}
