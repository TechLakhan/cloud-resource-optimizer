package com.cro.resource_monitoring_service.repository;

import com.cro.resource_monitoring_service.entity.PodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PodRepository extends JpaRepository<PodEntity, Long> {
    List<PodEntity> findByUsername(String username);
    List<PodEntity> findByNamespaceAndUsername(String namespace, String username);
}
