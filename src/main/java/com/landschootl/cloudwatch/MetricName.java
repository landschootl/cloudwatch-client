package com.landschootl.cloudwatch;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MetricName {

    METRIC_NAME_1("METRIC_NAME_1"),
    METRIC_NAME_2("METRIC_NAME_2"),
    METRIC_NAME_3("METRIC_NAME_3");

    @Getter
    private String value;
}
