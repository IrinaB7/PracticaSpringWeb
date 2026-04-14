package org.eduardomango.practicaspringweb.model.exceptions;

import java.util.NoSuchElementException;

public class SaleNotFoundException extends NoSuchElementException {
  public SaleNotFoundException(String message) {
    super(message);
  }

  public SaleNotFoundException() {
  }
}
