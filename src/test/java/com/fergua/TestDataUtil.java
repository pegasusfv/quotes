package com.fergua;

import com.fergua.domain.dto.QuoteDto;
import com.fergua.domain.entities.QuoteEntity;

public final class TestDataUtil {

  private TestDataUtil() {
  }

  public static QuoteEntity createQuoteA() {
    return QuoteEntity.builder()
        .id(1L)
        .author("Karl max")
        .quote("test1")
        .build();
  }

  public static QuoteDto createQuoteDtoA() {
    return QuoteDto.builder()
        .id(1L)
        .quote("Karl max")
        .author("test1")
        .build();
  }

  public static QuoteEntity createQuoteB() {
    return QuoteEntity.builder()
        .id(2L)
        .author("Shakespeare")
        .quote("test2")
        .build();
  }

  public static QuoteDto createQuoteDtoB() {
    return QuoteDto.builder()
        .id(2L)
        .author("Shakespeare")
        .quote("test2")
        .build();
  }
}
