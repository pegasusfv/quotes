package com.fergua.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.fergua.TestDataUtil;
import com.fergua.domain.entities.QuoteEntity;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class QuoteEntityRepositoryIntegrationTests {

  private final QuoteRepository underTest;

  @Autowired
  public QuoteEntityRepositoryIntegrationTests(QuoteRepository underTest) {
    this.underTest = underTest;
  }

  @Test
  public void testQuoteCanBeCreatedAndRecalled() {
    QuoteEntity quoteEntity = TestDataUtil.createQuoteA();
    underTest.save(quoteEntity);
    Optional<QuoteEntity> result = underTest.findById(quoteEntity.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(quoteEntity);
  }

  @Test
  public void testManyQuotesCanBeCreatedAndRecalled() {
    QuoteEntity quoteEntityA = TestDataUtil.createQuoteA();
    underTest.save(quoteEntityA);
    QuoteEntity quoteEntityB = TestDataUtil.createQuoteB();
    underTest.save(quoteEntityB);

    Iterable<QuoteEntity> result = underTest.findAll();
    assertThat(result)
        .hasSize(2).
        containsExactly(quoteEntityA, quoteEntityB);
  }
}
