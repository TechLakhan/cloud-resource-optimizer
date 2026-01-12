package com.cro.resource_monitoring_service.service;

import com.cro.resource_monitoring_service.dto.PodMetricsResponse;
import com.cro.resource_monitoring_service.entity.PodEntity;
import com.cro.resource_monitoring_service.repository.PodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PodMetricsService {

    private final PodRepository podRepository;

    public PodMetricsService(PodRepository podRepository) {
        this.podRepository = podRepository;
    }


    public List<PodMetricsResponse> fetchPodsMetrics(final String username) {
        return podRepository.findByUsername(username)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<PodMetricsResponse> getPodsByNamespaceAndUsername(final String namespace, final String username) {
        return podRepository.findByNamespaceAndUsername(namespace, username)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private PodMetricsResponse toResponse(PodEntity pod) {
        return new PodMetricsResponse(
                pod.getPodName(),
                pod.getNamespace(),
                pod.getNodeName(),
                pod.getCpuUsage(),
                pod.getMemoryUsage(),
                pod.getRestartCount(),
                pod.getStatus(),
                pod.isThrottled()
        );
    }
}
