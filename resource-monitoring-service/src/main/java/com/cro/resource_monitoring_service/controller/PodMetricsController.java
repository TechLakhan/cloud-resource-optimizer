package com.cro.resource_monitoring_service.controller;

import com.cro.resource_monitoring_service.dto.PodMetricsResponse;
import com.cro.resource_monitoring_service.service.PodMetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/pods")
public class PodMetricsController {

    private final PodMetricsService podMetricsService;

    public PodMetricsController(PodMetricsService podMetricsService) {
        this.podMetricsService = podMetricsService;
    }

    @GetMapping("/metrics")
    public ResponseEntity<List<PodMetricsResponse>> getPodMetrics(
            @RequestHeader ("X-CRO-Username") final String username) {
        return ResponseEntity.ok(podMetricsService.fetchPodsMetrics(username));
    }
}
