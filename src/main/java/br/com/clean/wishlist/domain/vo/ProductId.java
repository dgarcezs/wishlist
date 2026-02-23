package br.com.clean.wishlist.domain.vo;

import br.com.clean.wishlist.domain.exception.BusinessException;

public record ProductId(String value) {

  public ProductId {
    if (value == null || value.isBlank()) {
      throw new BusinessException("Product ID cannot be null or blank");
    }
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof ProductId(String otherValue))) {
      return false;
    }
    return value.equals(otherValue);
  }

  @Override
  public String toString() {
    return value;
  }
}