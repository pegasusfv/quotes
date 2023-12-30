package com.fergua.controllers;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fergua.TestDataUtil;
import com.fergua.domain.dto.QuoteDto;
import com.fergua.domain.entities.QuoteEntity;
import com.fergua.services.QuoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class QuoteControllerIntegrationTests {

  private final QuoteService quoteService;
  private final MockMvc mockMvc;
  private final ObjectMapper objectMapper;

  @Autowired
  public QuoteControllerIntegrationTests(MockMvc mockMvc, QuoteService quoteService) {
    this.mockMvc = mockMvc;
    this.quoteService = quoteService;
    this.objectMapper = new ObjectMapper();
  }

  @Test
  public void testCreateQuoteSuccessfullyReturnSavedQuote() throws Exception {
    QuoteDto quoteA = TestDataUtil.createQuoteDtoA();
    quoteA.setId(null);
    String quoteJson = objectMapper.writeValueAsString(quoteA);

    mockMvc.perform(
        MockMvcRequestBuilders.post("/quotes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(quoteJson)
            .content(quoteJson)
    ).andExpect(
        MockMvcResultMatchers.status().isCreated()
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$.author").value("test1")
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$.quote").value("Karl max")
    );
  }

  @Test
  public void testListQuotesReturnsList() throws Exception {
    QuoteEntity testQuoteEntityA = TestDataUtil.createQuoteA();
    quoteService.save(testQuoteEntityA);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/quotes")
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk()
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$[0].quote").value("test1")
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$[0].author").value("Karl max")
    );
  }

  @Test
  public void testGetQuoteReturnsQuoteWhenExist() throws Exception {
    QuoteEntity testQuoteEntityA = TestDataUtil.createQuoteA();
    quoteService.save(testQuoteEntityA);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/quotes/1")
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk()
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$.quote").value("test1")
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$.author").value("Karl max")
    );
  }

  @Test
  public void testGetQuoteHttpStatus404WhenNoExist() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders.get("/quotes/99")
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testGetRandomQuoteReturnsQuote() throws Exception {
    QuoteEntity testQuoteEntityA = TestDataUtil.createQuoteA();
    quoteService.save(testQuoteEntityA);

    QuoteEntity testQuoteEntityB = TestDataUtil.createQuoteB();
    quoteService.save(testQuoteEntityB);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/quotes/random")
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk()
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$.quote", anyOf(
            is("test1"),
            is("test2")))
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$.author", anyOf(
            is("Karl max"),
            is("Shakespeare")))
    );
  }
}
