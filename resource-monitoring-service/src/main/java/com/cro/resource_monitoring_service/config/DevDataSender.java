package com.cro.resource_monitoring_service.config;

import com.cro.resource_monitoring_service.entity.PodEntity;
import com.cro.resource_monitoring_service.repository.PodRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevDataSender {

    private final PodRepository podRepository;

    public DevDataSender(PodRepository podRepository) {
        this.podRepository = podRepository;
    }

    @PostConstruct
    public void seed() {
        if (podRepository.count() > 0) return;

        podRepository.save(
                new PodEntity(
                        null,
                        "default",
                        "node-1",
                        120,
                        256,
                        0,
                        "Running",
                        false,
                        "ram"
                )
        );
    }
}
