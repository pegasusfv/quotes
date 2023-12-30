package com.fergua.repositories;

import com.fergua.domain.entities.QuoteEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends CrudRepository<QuoteEntity, Long> {

  @Query("SELECT a FROM QuoteEntity a ORDER BY RAND()")
  List<QuoteEntity> findRandomQuote(Pageable pageable);

}
