package com.unexpectexpected.n26.rest.transfer;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDTO {

    private double amount;
    private long timestamp;

}
