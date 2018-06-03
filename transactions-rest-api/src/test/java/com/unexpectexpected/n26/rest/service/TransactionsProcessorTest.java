package com.unexpectexpected.n26.rest.service;

import com.unexpectexpected.n26.rest.transfer.TransactionDTO;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.Assert.*;

public class TransactionsProcessorTest {

    private TransactionsProcessor transactionsProcessor = new TransactionsProcessor();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void whenAddValidTransaction() throws Exception {
        long timestamp = Instant.now().atOffset(ZoneOffset.UTC).minusSeconds(30).toInstant().toEpochMilli();
        TransactionDTO transactionDTO = new TransactionDTO();

        transactionDTO.setAmount(123.24d);
        transactionDTO.setTimestamp(timestamp);

        assertTrue(transactionsProcessor.add(transactionDTO));

    }

    @Test
    public void whenAddOldTransaction() throws Exception {

        long timestamp = Instant.now().atOffset(ZoneOffset.UTC).toInstant().minusSeconds(60).toEpochMilli();

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(123.00d);
        transactionDTO.setTimestamp(timestamp);

        assertFalse(transactionsProcessor.add(transactionDTO));

    }

    @Test
    public void whenAddTransactionFromFuture() throws Exception {

        long timestamp = Instant.now().atOffset(ZoneOffset.UTC).toInstant().plusSeconds(60).toEpochMilli();

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(123.00d);
        transactionDTO.setTimestamp(timestamp);

        assertFalse(transactionsProcessor.add(transactionDTO));

    }

}