package com.unexpectexpected.n26.rest.controller;

import com.unexpectexpected.n26.rest.service.StatisticsBuilder;
import com.unexpectexpected.n26.rest.service.TransactionsProcessor;
import com.unexpectexpected.n26.rest.transfer.StatisticsDTO;
import com.unexpectexpected.n26.rest.transfer.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RestController {

    @Autowired
    private TransactionsProcessor transactionsProcessor;

    @Autowired
    private StatisticsBuilder statisticsBuilder;

    @PostMapping(value = "/transactions")
    public ResponseEntity submitTransaction(@RequestBody TransactionDTO transaction) {
        if (transactionsProcessor.add(transaction)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/statistics")
    @ResponseBody
    public StatisticsDTO getStatistics() {
        return statisticsBuilder.getStatistics();
    }


}
