package com.rashidi.transferservice.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class TransferServiceConfig {

  // Monitoring beans
  @Bean
  ObservationRegistry observationRegistry() {
    return ObservationRegistry.create();
  }

  // To have the @Observed support we need to register this aspect
  @Bean
  ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
    return new ObservedAspect(observationRegistry);
  }
}
