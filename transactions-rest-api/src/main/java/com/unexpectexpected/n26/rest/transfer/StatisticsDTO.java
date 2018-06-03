package com.unexpectexpected.n26.rest.transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticsDTO {

    private double sum;
    private double avg;
    private double max;
    private double min;
    private long count;

    public StatisticsDTO() {
    }

    public StatisticsDTO(double sum, double avg, double max, double min, long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

}
