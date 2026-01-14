package com.cro.resource_monitoring_service.service;

import com.cro.resource_monitoring_service.dto.NamespaceSummaryResponse;
import com.cro.resource_monitoring_service.entity.PodEntity;
import com.cro.resource_monitoring_service.exception.UnauthorizedOperationException;
import com.cro.resource_monitoring_service.repository.PodRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NamespaceSummaryService {

    private final PodRepository podRepository;

    public NamespaceSummaryService(PodRepository podRepository) {
        this.podRepository = podRepository;
    }

    public List<NamespaceSummaryResponse> getNamespaceSummary(final String username, final String namespace) {
        List<PodEntity> pods;

        if (namespace != null && !namespace.isBlank()) {
            pods = podRepository.findByUsernameAndNamespace(username, namespace);
        } else {
            pods = podRepository.findByUsername(username);
        }

        Map<String, List<PodEntity>> namespaceMap = pods.stream().collect(Collectors.groupingBy(PodEntity::getNamespace));

        return namespaceMap.entrySet().stream()
                .map(
                        entry -> {
                            String namespaceName = entry.getKey();
                            List<PodEntity> namespacePods = entry.getValue();

                            int totalPods = namespacePods.size();

                            int runningPods = (int) namespacePods.stream()
                                    .filter(p -> "Running".equalsIgnoreCase(p.getStatus())).count();

                            int failedPods = (int) namespacePods.stream()
                                    .filter(p -> "Failed".equalsIgnoreCase(p.getStatus())).count();

                            int totalCpuUsage = (int) namespacePods.stream()
                                    .mapToLong(PodEntity::getCpuUsage)
                                    .sum();

                            int totalMemoryUsage = (int) namespacePods.stream()
                                    .mapToLong(PodEntity::getMemoryUsage)
                                    .sum();

                            long cpuRequested = 0;
                            long memoryRequested = 0;

                            double estimatedCostPerHr = (totalCpuUsage * 0.05) + (totalMemoryUsage * 0.04);

                            boolean highUsageAlert = totalCpuUsage > 2000 || totalMemoryUsage > 4096;

                            return new NamespaceSummaryResponse(
                                    namespaceName,
                                    totalPods,
                                    runningPods,
                                    failedPods,
                                    cpuRequested,
                                    memoryRequested,
                                    totalCpuUsage,
                                    totalMemoryUsage,
                                    estimatedCostPerHr,
                                    highUsageAlert
                            );
                        }).toList();
    }

    public void validateRequest(String username) throws UnauthorizedOperationException {
        if (username == null || StringUtils.isBlank(username)) {
            throw new UnauthorizedOperationException("Invalid or incorrect username");
        }
    }
}
