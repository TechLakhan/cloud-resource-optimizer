package com.cro.resource_monitoring_service.service;

import com.cro.resource_monitoring_service.dto.PodMetricsResponse;
import com.cro.resource_monitoring_service.dto.ResourceHotspotResponse;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotspotMetricsService {

    private final static String MEMORY = "memory";
    private final PodMetricsService podMetricsService;

    public HotspotMetricsService(PodMetricsService podMetricsService) {
        this.podMetricsService = podMetricsService;
    }

    public List<ResourceHotspotResponse> getResourceHotspot(String username, String type, int limit) {
        List<PodMetricsResponse> pods = podMetricsService.fetchPodsMetrics();
        Comparator<PodMetricsResponse> comparator = MEMORY.equalsIgnoreCase(type)
                ? Comparator.comparingInt(PodMetricsResponse::getMemoryUsageMi).reversed()
                : Comparator.comparingInt(PodMetricsResponse::getCpuUsageMilli).reversed();

        return pods.stream()
                .sorted(comparator)
                .limit(limit)
                .map(pod -> new ResourceHotspotResponse(
                        pod.getPodName(),
                        pod.getNamespace(),
                        pod.getNodeName(),
                        pod.getCpuUsageMilli(),
                        pod.getMemoryUsageMi()
                ))
                .collect(Collectors.toList());
    }
}
