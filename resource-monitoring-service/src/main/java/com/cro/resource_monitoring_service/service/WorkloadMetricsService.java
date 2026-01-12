package com.cro.resource_monitoring_service.service;

import com.cro.resource_monitoring_service.dto.PodMetricsResponse;
import com.cro.resource_monitoring_service.dto.WorkloadSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkloadMetricsService {

    private final PodMetricsService podMetricsService;

    public WorkloadMetricsService(PodMetricsService podMetricsService) {
        this.podMetricsService = podMetricsService;
    }

    public List<WorkloadSummaryResponse> fetchWorkloadSummary(String username) {

        List<PodMetricsResponse> pods = podMetricsService.fetchPodsMetrics(username);

        Map<String, List<PodMetricsResponse>> groupedPods =
                pods.stream()
                        .collect(Collectors.groupingBy(
                                pod -> extractWorkloadName(pod.getPodName())
                                        + "|" + pod.getNamespace()
                        ));

        List<WorkloadSummaryResponse> response = new ArrayList<>();

        for (Map.Entry<String, List<PodMetricsResponse>> entry : groupedPods.entrySet()) {

            List<PodMetricsResponse> workloadPods = entry.getValue();
            PodMetricsResponse samplePod = workloadPods.get(0);

            int totalCpu = workloadPods.stream()
                    .mapToInt(PodMetricsResponse::getCpuUsageMilli)
                    .sum();

            int totalMemory = workloadPods.stream()
                    .mapToInt(PodMetricsResponse::getMemoryUsageMi)
                    .sum();

            boolean degraded = workloadPods.stream()
                    .anyMatch(pod ->
                            pod.getRestartCount() > 0 ||
                                    !"Running".equalsIgnoreCase(pod.getStatus())
                    );

            response.add(
                    new WorkloadSummaryResponse(
                            extractWorkloadName(samplePod.getPodName()),
                            "Deployment",
                            samplePod.getNamespace(),
                            workloadPods.size(),
                            totalCpu,
                            totalMemory,
                            degraded ? "Degraded" : "Healthy"
                    )
            );
        }
        return response;
    }

    private String extractWorkloadName(String podName) {
        return podName.contains("-")
                ? podName.substring(0, podName.lastIndexOf("-"))
                : podName;
    }
}