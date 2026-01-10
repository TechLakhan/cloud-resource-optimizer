package com.cro.resource_monitoring_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class CostRecommendationResponse {

    private String resourceType;
    private String resourceName;
    private String namespace;

    private String issue;
    private String recommendation;
    private String impact;

    private double estimatedSavingsPerMonth;

}
