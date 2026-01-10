package com.cro.resource_monitoring_service.controller;

import com.cro.resource_monitoring_service.dto.ClusterSummaryResponse;
import com.cro.resource_monitoring_service.service.ClusterSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user/cluster")
public class ClusterSummaryController {

    private final ClusterSummaryService clusterSummaryService;

    public ClusterSummaryController(ClusterSummaryService clusterSummaryService) {
        this.clusterSummaryService = clusterSummaryService;
    }

    @GetMapping(value = "/summary")
    public ResponseEntity<ClusterSummaryResponse> getAllClusterSummary(
            @RequestHeader ("X-CRO-Username") String username,
            @RequestHeader ("X-CRO-Role") String role
    ) {
        if (!role.equals("ROLE-ADMIN") && !role.equals("ROLE-USER")) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(clusterSummaryService.getClusterSummary(username));
    }
}
