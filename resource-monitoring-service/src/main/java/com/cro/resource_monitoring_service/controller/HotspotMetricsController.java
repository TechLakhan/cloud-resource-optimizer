package com.cro.resource_monitoring_service.controller;

import com.cro.resource_monitoring_service.dto.ResourceHotspotResponse;
import com.cro.resource_monitoring_service.service.HotspotMetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class HotspotMetricsController {

    private final HotspotMetricsService hotspotMetricsService;

    public HotspotMetricsController(HotspotMetricsService hotspotMetricsService) {
        this.hotspotMetricsService = hotspotMetricsService;
    }
    @GetMapping("/hotspot")
    public ResponseEntity<List<ResourceHotspotResponse>> fetchHotspots(
            @RequestHeader ("X-CRO-Username") String username,
            @RequestParam (defaultValue = "cpu") String type,
            @RequestParam (defaultValue = "5") int limit
    ) {
        return ResponseEntity.ok(hotspotMetricsService.getResourceHotspot(username, type, limit));
    }

}
