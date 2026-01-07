package com.cro.resource_monitoring_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class WorkloadSummaryResponse {
    private String workloadName;
    private String workloadType;
    private String namespace;
    private int podCount;
    private int totalCpuUsage;
    private int totalMemoryUsage;
    private String health;
}
