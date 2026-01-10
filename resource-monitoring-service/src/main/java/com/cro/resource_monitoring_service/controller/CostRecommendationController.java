package com.cro.resource_monitoring_service.controller;

import com.cro.resource_monitoring_service.dto.CostRecommendationResponse;
import com.cro.resource_monitoring_service.service.CostRecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class CostRecommendationController {

    private final CostRecommendationService service;

    public CostRecommendationController(CostRecommendationService service) {
        this.service = service;
    }

    @GetMapping(value = "/recommendations")
    public ResponseEntity<List<CostRecommendationResponse>> getRecommendations(
            @RequestHeader ("X-CRO-Username") final String username
    ) {
        return ResponseEntity.ok(service.getCostRecommendations(username));
    }
}
