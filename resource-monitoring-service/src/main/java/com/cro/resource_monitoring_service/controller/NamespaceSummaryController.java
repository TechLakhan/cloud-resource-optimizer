package com.cro.resource_monitoring_service.controller;

import com.cro.resource_monitoring_service.dto.NamespaceSummaryResponse;
import com.cro.resource_monitoring_service.dto.PodMetricsResponse;
import com.cro.resource_monitoring_service.exception.InvalidRequestException;
import com.cro.resource_monitoring_service.exception.UnauthorizedOperationException;
import com.cro.resource_monitoring_service.service.NamespaceSummaryService;
import com.cro.resource_monitoring_service.service.PodMetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/namespace")
public class NamespaceSummaryController {

    private final NamespaceSummaryService namespaceSummaryService;
    private final PodMetricsService podMetricsService;

    public NamespaceSummaryController(NamespaceSummaryService namespaceSummaryService, PodMetricsService podMetricsService) {
        this.namespaceSummaryService = namespaceSummaryService;
        this.podMetricsService = podMetricsService;
    }

    @GetMapping("/{namespace}/summary")
    public ResponseEntity<NamespaceSummaryResponse> getNamespaceSummaryResponse(
            @PathVariable String namespace,
            @RequestHeader ("X-CRO-Username") String username) throws InvalidRequestException, UnauthorizedOperationException {

        namespaceSummaryService.validateRequest(namespace, username);
        List<PodMetricsResponse> pods = podMetricsService.getPodsByNamespace(namespace);
        NamespaceSummaryResponse Response = namespaceSummaryService.buildSummary(namespace, pods);

        return ResponseEntity.ok(Response);
    }
}
