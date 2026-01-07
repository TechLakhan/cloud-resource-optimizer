package com.cro.resource_monitoring_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class ResourceHotspotResponse {

    private String podName;
    private String namespace;
    private String nodeName;
    private int cpuUsageMilli;
    private int memoryUsageMi;
}
