package com.cro.resource_monitoring_service.dto;

public class ClusterSummaryResponse {

    private String clusterName;
    private int totalNodes;
    private int activeNodes;

    private int totalPods;
    private int activePods;

    private double cpuUsagePercent;
    private double memoryUsagePercent;

    private String status;

    public ClusterSummaryResponse(String clusterName, int totalNodes, int activeNodes, int totalPods, int activePods, double cpuUsagePercent, double memoryUsagePercent, String status) {
        this.clusterName = clusterName;
        this.totalNodes = totalNodes;
        this.activeNodes = activeNodes;
        this.totalPods = totalPods;
        this.activePods = activePods;
        this.cpuUsagePercent = cpuUsagePercent;
        this.memoryUsagePercent = memoryUsagePercent;
        this.status = status;
    }

    public String getClusterName() {
        return clusterName;
    }

    public int getTotalNodes() {
        return totalNodes;
    }

    public int getActiveNodes() {
        return activeNodes;
    }

    public int getTotalPods() {
        return totalPods;
    }

    public int getActivePods() {
        return activePods;
    }

    public double getCpuUsagePercent() {
        return cpuUsagePercent;
    }

    public double getMemoryUsagePercent() {
        return memoryUsagePercent;
    }

    public String getStatus() {
        return status;
    }
}
