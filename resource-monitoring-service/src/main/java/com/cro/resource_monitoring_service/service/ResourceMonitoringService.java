package com.cro.resource_monitoring_service.service;

import com.cro.resource_monitoring_service.model.ClusterResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceMonitoringService {

    public List<ClusterResource> getUserClusterDetails(String username) {
        return List.of(
                new ClusterResource("Prod-cluster", "60-70%", "45-50%"),
                new ClusterResource("Non-Prod-Cluster", "45-55%", "30-40%")
        );
    }

    public String triggerOptimization() {
        return "Optimization triggered successfully.";
    }
}
