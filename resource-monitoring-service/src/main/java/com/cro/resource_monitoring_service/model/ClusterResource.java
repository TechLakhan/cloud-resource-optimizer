package com.cro.resource_monitoring_service.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ClusterResource {

    private String clusterName;
    private String cpuUsage;
    private String memoryUsage;
}