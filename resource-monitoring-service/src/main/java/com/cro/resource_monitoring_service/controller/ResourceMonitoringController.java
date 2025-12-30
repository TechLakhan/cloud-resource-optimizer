package com.cro.resource_monitoring_service.controller;

import com.cro.resource_monitoring_service.service.ResourceMonitoringService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class ResourceMonitoringController {

    private final ResourceMonitoringService resourceMonitoringService;

    @GetMapping("/user/clusterinfo")
    public ResponseEntity<?> getClusters(@RequestHeader ("X-CRO-Username") String username,
                                         @RequestHeader ("X-CRO-Role") String role) {

        if (!role.equals("ROLE-ADMIN") && !(role.equals("ROLE-USER"))) {
            return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).build();
        }

        return ResponseEntity.ok(resourceMonitoringService.getUserClusterDetails(username));
    }

    @PostMapping("/admin/optimize")
    public ResponseEntity<?> optimizeCluster(@RequestHeader ("X-CRO-Role") String role) {

        if (!role.equals("ROLE-ADMIN")) {
            return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Admin access required");
        }
        return ResponseEntity.ok(resourceMonitoringService.triggerOptimization());
    }
}
