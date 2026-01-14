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
@RequestMapping("/user/namespaces")
public class NamespaceSummaryController {

    private final NamespaceSummaryService namespaceSummaryService;

    public NamespaceSummaryController(NamespaceSummaryService namespaceSummaryService) {
        this.namespaceSummaryService = namespaceSummaryService;
    }

    @GetMapping("/summary")
    public ResponseEntity<List<NamespaceSummaryResponse>> getNamespaceSummaryResponse(
            @RequestHeader ("X-CRO-Username") String username,
            @RequestParam(required = false) String namespace )
            throws UnauthorizedOperationException {

        namespaceSummaryService.validateRequest(username);
        List<NamespaceSummaryResponse> namespaceSummary = namespaceSummaryService.getNamespaceSummary(username, namespace);

        return ResponseEntity.ok(namespaceSummary);
    }
}
