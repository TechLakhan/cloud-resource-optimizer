package com.cro.resource_monitoring_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PodMetricsResponse {
    private String podName;
    private String namespace;
    private String nodeName;

    private int cpuUsageMilli;
    private int memoryUsageMi;

    private int restartCount;
    private String status;

    private boolean anomaly;


    public PodMetricsResponse(String podName, String namespace, String nodeName, int cpuUsageMilli, int memoryUsageMi, String status) {
        this.podName = podName;
        this.namespace = namespace;
        this.nodeName = nodeName;
        this.cpuUsageMilli = cpuUsageMilli;
        this.memoryUsageMi = memoryUsageMi;
        this.status = status;
    }
}
