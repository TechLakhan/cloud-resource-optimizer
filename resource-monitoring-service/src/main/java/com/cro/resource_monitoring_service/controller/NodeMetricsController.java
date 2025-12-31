package com.cro.resource_monitoring_service.controller;

import com.cro.resource_monitoring_service.dto.NodeMetricsResponse;
import com.cro.resource_monitoring_service.service.NodeMetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/nodes")
public class NodeMetricsController {

    private final NodeMetricsService nodeMetricsService;


    public NodeMetricsController(NodeMetricsService nodeMetricsService) {
        this.nodeMetricsService = nodeMetricsService;
    }

    @GetMapping("/metrics")
    public ResponseEntity<List<NodeMetricsResponse>> getNodeMetrics(
            @RequestHeader ("X-CRO-Username") String username) {
        return ResponseEntity.ok(nodeMetricsService.fetchNodeMetrics(username));
    }
}
