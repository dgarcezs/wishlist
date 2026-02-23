package br.com.clean.wishlist.domain.validation;

public interface ValidationHandler<T> {

  void validate(T input);
}
