package com.cro.resource_monitoring_service.service;

import com.cro.resource_monitoring_service.dto.NamespaceSummaryResponse;
import com.cro.resource_monitoring_service.dto.PodMetricsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NamespaceSummaryService {

    public NamespaceSummaryResponse buildSummary(String namespace, List<PodMetricsResponse> pods) {
        int totalPods = pods.size();
        int runningPods = 0;
        int failedPods = 0;

        long cpuUsed = 0;

        long memoryUsed = 0;

        for (PodMetricsResponse pod : pods) {

            if ("running".equalsIgnoreCase(pod.getStatus())) {
                runningPods++;
            } else if ("failed".equalsIgnoreCase(pod.getStatus())) {
                failedPods++;
            }

            cpuUsed += pod.getCpuUsageMilli();
            memoryUsed += pod.getMemoryUsageMi();
        }

        double cpuUsageCost = cpuUsed * 0.00001;
        double memoryUsageCost = memoryUsed * 0.00005;
        double estimatedCost = cpuUsageCost + memoryUsageCost;

        boolean highUsageAlert = cpuUsed < 10_000 || memoryUsed < 20_000;

        return new NamespaceSummaryResponse(
                namespace,
                totalPods,
                runningPods,
                failedPods,
                cpuUsed,
                memoryUsed,
                estimatedCost,
                highUsageAlert
        );


    }

    public void validateHeaders(String namespace, String username) {
        if (namespace == null || username == null) {
            throw new RuntimeException("Namespace & username must be passed in headers");
        }
    }
}
