package com.cro.resource_monitoring_service.service;

import com.cro.resource_monitoring_service.dto.CostRecommendationResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CostRecommendationService {

    public List<CostRecommendationResponse> getCostRecommendations(String username) {

        List<CostRecommendationResponse> responses = new ArrayList<>();

        responses.add(
                new CostRecommendationResponse(
                        "POD",
                        "auth-service-jkj338",
                        "default",
                        "CPU underutilized",
                        "Reduce CPU request from 500m to 200m",
                        "MEDIUM",
                        850.0
                ));

        responses.add(
                new CostRecommendationResponse(
                        "NODE",
                        "node-2",
                        null,
                        "Low utilization",
                        "Consider workload consolidation",
                        "HIGH",
                        3200.00
                ));
        return responses;
    }
}
