package com.cro.resource_monitoring_service.service;

import com.cro.resource_monitoring_service.dto.PodMetricsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PodMetricsService {

    public List<PodMetricsResponse> fetchPodsMetrics() {
        return List.of(
                new PodMetricsResponse(
                        "auth-service-jkj338",
                        "default",
                        "node-1",
                        120,
                        256,
                        0,
                        "Running",
                        false
                ),
                new PodMetricsResponse(
                        "resource-monitoring-def456",
                        "default",
                        "node-2",
                        850,
                        1800,
                        3,
                        "Running",
                        true
                )
        );
    }
}
