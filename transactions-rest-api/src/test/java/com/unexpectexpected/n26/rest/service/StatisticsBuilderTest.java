package com.unexpectexpected.n26.rest.service;

import com.unexpectexpected.n26.rest.transfer.StatisticsDTO;
import com.unexpectexpected.n26.rest.transfer.TransactionDTO;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class StatisticsBuilderTest {

    private StatisticsBuilder statisticsBuilder = new StatisticsBuilder();


    @Before
    public void setUp() {

        long timestamp = Instant.now().atOffset(ZoneOffset.UTC).toInstant().minusSeconds(10).toEpochMilli();

        TransactionDTO transaction1 = new TransactionDTO();
        transaction1.setAmount(1.00d);
        transaction1.setTimestamp(timestamp);

        TransactionDTO transaction2 = new TransactionDTO();
        transaction2.setAmount(3.00d);
        transaction2.setTimestamp(timestamp);

        List<TransactionDTO> transactions = new ArrayList<TransactionDTO>() {{
            add(transaction1);
            add(transaction2);
        }};

        statisticsBuilder.calculate(transactions);
    }

    @Test
    public void whenGetEmptyStatistics() throws Exception {

        StatisticsBuilder statisticsBuilder = new StatisticsBuilder();

        StatisticsDTO statistics = statisticsBuilder.getStatistics();

        assertEquals(statistics.getAvg(), 0d, 0.001);
        assertEquals(statistics.getSum(), 0d, 0.001);
        assertEquals(statistics.getMax(), 0d, 0.001);
        assertEquals(statistics.getMin(), 0d, 0.001);
        assertEquals(statistics.getCount(), 0l);
    }

    @Test
    public void whenGetStatistics() throws Exception {

        StatisticsDTO statistics = statisticsBuilder.getStatistics();

        assertEquals(statistics.getAvg(), 2d, 0.001);
        assertEquals(statistics.getSum(), 4d, 0.001);
        assertEquals(statistics.getMax(), 3d, 0.001);
        assertEquals(statistics.getMin(), 1d, 0.001);
        assertEquals(statistics.getCount(), 2l);

    }

    @Test
    public void whenCalculateNullStatistics() throws Exception {
        statisticsBuilder.calculate(null);
    }

}