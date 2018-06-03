package com.unexpectexpected.n26.rest.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unexpectexpected.n26.rest.service.StatisticsBuilder;
import com.unexpectexpected.n26.rest.service.TransactionsProcessor;
import com.unexpectexpected.n26.rest.transfer.StatisticsDTO;
import com.unexpectexpected.n26.rest.transfer.TransactionDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@WebMvcTest(RestController.class)
public class RestControllerTestIT {

    private MockMvc mockMvc;

    @InjectMocks
    private RestController restController = new RestController();

    @Mock
    private TransactionsProcessor transactionsProcessor;
    @Mock
    private StatisticsBuilder statisticsBuilder;

    private TransactionDTO transactionDTO;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        mockMvc = standaloneSetup(restController).build();

        transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(123.24d);
        transactionDTO.setTimestamp(LocalDateTime.now().toInstant(ZoneOffset.UTC).plusSeconds(60).toEpochMilli());

    }

    @Test
    public void whenSubmitValidTransaction() throws Exception {

        when(transactionsProcessor.add(anyObject())).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(transactionDTO);

        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    public void whenSubmitNotValidTransaction() throws Exception {

        when(transactionsProcessor.add(anyObject())).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(transactionDTO);

        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    public void whenGetStatistics() throws Exception {
        when(statisticsBuilder.getStatistics()).thenReturn(new StatisticsDTO());

        mockMvc.perform(get("/statistics").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}