package com.cro.resource_monitoring_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ClusterResource {

    private String clusterName;
    private String cpuUsage;
    private String memoryUsage;
}