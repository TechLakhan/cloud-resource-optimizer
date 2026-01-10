package com.cro.resource_monitoring_service.service;

import com.cro.resource_monitoring_service.dto.CostOptimizationResponse;
import com.cro.resource_monitoring_service.dto.PodMetricsResponse;
import com.cro.resource_monitoring_service.exception.InvalidRequestException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CostOptimizationService {

    private static final double CPU_COST_PER_CORE_PER_HOUR = 0.05;
    private static final double MEMORY_COST_PER_GB_PER_HOUR = 0.01;

    private final PodMetricsService podMetricsService;


    public CostOptimizationService(PodMetricsService podMetricsService) {
        this.podMetricsService = podMetricsService;
    }

    public List<CostOptimizationResponse> estimateCost(String username) throws InvalidRequestException {

        if (username == null || StringUtils.isBlank(username)) {
            throw new InvalidRequestException(username);
        }

        List<PodMetricsResponse> pods = podMetricsService.fetchPodsMetrics();

        return pods.stream()
                .map(pod -> {
                    double cpuCores = pod.getCpuUsageMilli();
                    double memoryGb = pod.getMemoryUsageMi();

                    double cpuCost = cpuCores * CPU_COST_PER_CORE_PER_HOUR;
                    double memoryCost = memoryGb * MEMORY_COST_PER_GB_PER_HOUR;
                    double totalCost = cpuCost + memoryCost;

                    return new CostOptimizationResponse(
                            pod.getPodName(),
                            pod.getNamespace(),
                            round(cpuCost),
                            round(memoryCost),
                            round(totalCost)
                    );
                })
                .collect(Collectors.toList());

    }

    private double round(double value) {
        return Math.round(value * 100.0);
    }
}
