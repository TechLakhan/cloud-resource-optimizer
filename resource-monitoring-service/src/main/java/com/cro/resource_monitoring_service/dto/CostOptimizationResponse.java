package com.cro.resource_monitoring_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class CostOptimizationResponse {

    private String podName;
    private String namespace;
    private double cpuCost;
    private double memoryCost;
    private double totalCost;
}
