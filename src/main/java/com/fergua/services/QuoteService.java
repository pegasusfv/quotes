package com.fergua.services;

import com.fergua.domain.entities.QuoteEntity;

import java.util.List;
import java.util.Optional;

public interface QuoteService {

  QuoteEntity save(QuoteEntity quoteEntity);

  List<QuoteEntity> findAll();

  Optional<QuoteEntity> findOne(Long id);

  Optional<QuoteEntity> findRandomQuote();

}
