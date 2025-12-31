package com.cro.resource_monitoring_service.service;

import com.cro.resource_monitoring_service.dto.ClusterSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ClusterSummaryService {

    public ClusterSummaryResponse getClusterSummary(String username) {
        return new ClusterSummaryResponse(
                "cro-cluster",
                5,
                5,
                120,
                110,
                62.5,
                71.8,
                "HEALTHY"
        );
    }
}
