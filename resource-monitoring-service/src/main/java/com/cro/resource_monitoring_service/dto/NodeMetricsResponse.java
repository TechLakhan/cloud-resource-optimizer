package com.cro.resource_monitoring_service.dto;

import lombok.Getter;

@Getter
public class NodeMetricsResponse {

    private final String nodeName;
    private final String status;
    private final double cpuCapacity;
    private final double cpuUsed;
    private final double cpuUsagePercent;
    private final long memoryCapacityMb;
    private final long memoryUsedMb;
    private final long memeryUsagePercent;
    private final int podCount;
    private final String role;
    private final String condition;

    public NodeMetricsResponse(String nodeName, String status, double cpuCapacity,
                               double cpuUsed, double cpuUsagePercent, long memoryCapacityMb,
                               long memoryUsedMb, long memeryUsagePercent, int podCount, String role,
                               String condition) {
        this.nodeName = nodeName;
        this.status = status;
        this.cpuCapacity = cpuCapacity;
        this.cpuUsed = cpuUsed;
        this.cpuUsagePercent = cpuUsagePercent;
        this.memoryCapacityMb = memoryCapacityMb;
        this.memoryUsedMb = memoryUsedMb;
        this.memeryUsagePercent = memeryUsagePercent;
        this.podCount = podCount;
        this.role = role;
        this.condition = condition;
    }
}
