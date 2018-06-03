package com.unexpectexpected.n26.rest.service;

import com.unexpectexpected.n26.rest.service.util.DateTimeUtils;
import com.unexpectexpected.n26.rest.transfer.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionsProcessor {

    private static final long statisticsTimeThreshold = 60;

    @Autowired
    private StatisticsBuilder statisticsBuilder;

    private List<TransactionDTO> transactionsList = Collections.synchronizedList(new LinkedList<>());

    public boolean add(TransactionDTO transaction) {

        if (isTransactionTimeValid(transaction)) {
            synchronized (transactionsList) {
                transactionsList.add(transaction);
            }
            return true;
        }

        return false;
    }

    private boolean isTransactionTimeValid(TransactionDTO transactionDTO) {
        return DateTimeUtils.olderThan(transactionDTO.getTimestamp(), statisticsTimeThreshold);
    }


    //every second cleans up the list of transactions and rebuilds statistics
    @Scheduled(cron = "0/1 * * * * ?")
    public void refreshTransactions() {

        synchronized (transactionsList) {
            transactionsList = transactionsList.stream().filter(this::isTransactionTimeValid).collect(Collectors.toCollection(LinkedList::new));
        }
        statisticsBuilder.calculate(transactionsList);
    }

}
