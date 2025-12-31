package com.cro.resource_monitoring_service.service;

import com.cro.resource_monitoring_service.dto.NodeMetricsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeMetricsService {

    public List<NodeMetricsResponse> fetchNodeMetrics(String username) {
        return List.of(
                new NodeMetricsResponse(
                        "node-1",
                        "READY",
                        8.0,
                        5.2,
                        65.0,
                        32768,
                        20480,
                        62,
                        42,
                        "worker",
                        "Healthy"
                ),
                new NodeMetricsResponse(
                        "node-2",
                        "READY",
                        16.0,
                        13.1,
                        81.8,
                        65536,
                        53248,
                        81,
                        58,
                        "worker",
                        "Warning"
                )
        );
    }
}
