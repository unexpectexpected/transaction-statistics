package com.unexpectexpected.n26.rest.service;

import com.unexpectexpected.n26.rest.transfer.StatisticsDTO;
import com.unexpectexpected.n26.rest.transfer.TransactionDTO;
import org.springframework.stereotype.Component;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatisticsBuilder {

    private StatisticsDTO statistics = new StatisticsDTO();

    public StatisticsDTO getStatistics() {
        return statistics;
    }

    public void calculate(List<TransactionDTO> transactions) {

        if (transactions == null) return;

        DoubleSummaryStatistics stats = transactions.stream().collect(Collectors.summarizingDouble(TransactionDTO::getAmount));

        statistics.setAvg(stats.getAverage());
        statistics.setSum(stats.getSum());
        statistics.setMax(stats.getMax());
        statistics.setMin(stats.getMin());
        statistics.setCount(stats.getCount());

    }

}
