package com.cro.resource_monitoring_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class NamespaceSummaryResponse {

    private String namespace;
    private int totalPods;
    private int runningPods;
    private int failedPods;

    private long totalCpuRequestedMilli;
    private long totalCpuUsedMilli;

    private long totalMemoryRequestedMi;
    private long totalMemoryUsedMi;

    private double estimatedCostPerHour;
    private boolean highUsageAlert;

    public NamespaceSummaryResponse(String namespace, int totalPods, int runningPods,
                                    int failedPods, long totalCpuUsedMilli, long totalMemoryUsedMi,
                                    double estimatedCostPerHour, boolean highUsageAlert) {
        this.namespace = namespace;
        this.totalPods = totalPods;
        this.runningPods = runningPods;
        this.failedPods = failedPods;
        this.totalCpuUsedMilli = totalCpuUsedMilli;
        this.totalMemoryUsedMi = totalMemoryUsedMi;
        this.estimatedCostPerHour = estimatedCostPerHour;
        this.highUsageAlert = highUsageAlert;
    }
}
