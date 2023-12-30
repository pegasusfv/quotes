package com.fergua.controllers;

import com.fergua.domain.dto.QuoteDto;
import com.fergua.domain.entities.QuoteEntity;
import com.fergua.mappers.Mapper;
import com.fergua.services.QuoteService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {

  private final QuoteService quoteService;

  private final Mapper<QuoteDto, QuoteEntity> quoteMapper;

  public QuoteController(QuoteService quoteService, Mapper<QuoteDto, QuoteEntity> quoteMapper) {
    this.quoteService = quoteService;
    this.quoteMapper = quoteMapper;
  }

  @PostMapping(path = "/quotes")
  public ResponseEntity<QuoteDto> createQuote(@RequestBody QuoteDto quote) {
    var quoteEntity = quoteMapper.mapFrom(quote, QuoteEntity.class);
    var savedQuoteEntity = quoteService.save(quoteEntity);
    var quoteDto = quoteMapper.mapTo(savedQuoteEntity, QuoteDto.class);
    return new ResponseEntity<>(quoteDto, HttpStatus.CREATED);
  }

  @GetMapping(path = "/quotes")
  public List<QuoteDto> listQuotes() {
    var quotes = quoteService.findAll();
    return quotes.stream()
        .map(ent -> quoteMapper.mapTo(ent, QuoteDto.class))
        .toList();
  }

  @GetMapping(path = "/quotes/{id}")
  public ResponseEntity<QuoteDto> getQuote(@PathVariable("id") Long id) {
    Optional<QuoteEntity> quote = quoteService.findOne(id);
    return quote.map(quoteEntity -> {
      QuoteDto quoteDto = quoteMapper.mapTo(quoteEntity, QuoteDto.class);
      return new ResponseEntity<>(quoteDto, HttpStatus.OK);
    }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping(path = "/quotes/random")
  public ResponseEntity<QuoteDto> getRandomQuote() {
    Optional<QuoteEntity> quote = quoteService.findRandomQuote();
    return quote.map(quoteEntity -> {
      QuoteDto quoteDto = quoteMapper.mapTo(quoteEntity, QuoteDto.class);
      return new ResponseEntity<>(quoteDto, HttpStatus.OK);
    }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
