package com.cro.resource_monitoring_service.controller;

import com.cro.resource_monitoring_service.dto.CostOptimizationResponse;
import com.cro.resource_monitoring_service.exception.InvalidRequestException;
import com.cro.resource_monitoring_service.service.CostOptimizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class CostOptimizationController {

    private final CostOptimizationService costOptimizationService;


    public CostOptimizationController(CostOptimizationService costOptimizationService) {
        this.costOptimizationService = costOptimizationService;
    }

    @GetMapping("/cost")
    public ResponseEntity<List<CostOptimizationResponse>> fetchEstimatedCost(
            @RequestHeader ("X-CRO-Username") String username
    ) throws InvalidRequestException {
        return ResponseEntity.ok(costOptimizationService.estimateCost(username));
    }
}
