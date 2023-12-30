package com.fergua.services.impl;

import com.fergua.domain.entities.QuoteEntity;
import com.fergua.repositories.QuoteRepository;
import com.fergua.services.QuoteService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class QuoteServiceImpl implements QuoteService {

  private final QuoteRepository quoteRepository;

  public QuoteServiceImpl(QuoteRepository quoteRepository) {
    this.quoteRepository = quoteRepository;
  }

  @Override
  public QuoteEntity save(QuoteEntity quoteEntity) {
    return quoteRepository.save(quoteEntity);
  }

  @Override
  public List<QuoteEntity> findAll() {
    return StreamSupport.stream(quoteRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<QuoteEntity> findOne(Long id) {
    return quoteRepository.findById(id);
  }

  @Override
  public Optional<QuoteEntity> findRandomQuote() {
    var randomQuotes = quoteRepository.findRandomQuote(PageRequest.of(0, 1));
    return randomQuotes.isEmpty() ? Optional.empty() : Optional.of(randomQuotes.get(0));
  }
}
