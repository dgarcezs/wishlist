package br.com.clean.wishlist.adapters.input.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record ValidationErrorDetailDTO(String code, String message, String field) {

  public ValidationErrorDetailDTO(String code, String message) {
    this(code, message, null);
  }
}
