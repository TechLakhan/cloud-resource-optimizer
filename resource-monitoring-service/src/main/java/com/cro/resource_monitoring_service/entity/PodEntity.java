package com.cro.resource_monitoring_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Pods", schema = "resource_monitoring")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String podName;

    @Column(nullable = false)
    private String namespace;

    @Column(nullable = false)
    private String nodeName;

    @Column(nullable = false)
    private int cpuUsage;

    @Column(nullable = false)
    private int memoryUsage;

    @Column(nullable = false)
    private int restartCount;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private boolean throttled;

    @Column(nullable = false)
    private String username;

    public PodEntity(String podName, String namespace, String nodeName, int cpuUsage, int memoryUsage, int restartCount, String status, boolean throttled, String username) {
        this.podName = podName;
        this.namespace = namespace;
        this.nodeName = nodeName;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.restartCount = restartCount;
        this.status = status;
        this.throttled = throttled;
        this.username = username;
    }
}
