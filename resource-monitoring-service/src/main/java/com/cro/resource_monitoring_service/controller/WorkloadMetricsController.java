package com.cro.resource_monitoring_service.controller;

import com.cro.resource_monitoring_service.dto.WorkloadSummaryResponse;
import com.cro.resource_monitoring_service.service.WorkloadMetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class WorkloadMetricsController {

    private final WorkloadMetricsService service;

    public WorkloadMetricsController(WorkloadMetricsService service) {
        this.service = service;
    }

    @GetMapping("/workloads")
    public ResponseEntity<List<WorkloadSummaryResponse>> getWorkloads(
            @RequestHeader ("X-CRO-Username") String username
    ) {
        return ResponseEntity.ok(service.fetchWorkloadSummary(username));
    }
}
