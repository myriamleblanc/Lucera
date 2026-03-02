package com.lucera.backend.config;

import com.lucera.backend.domain.StressSignal;
import com.lucera.backend.service.ThrottlingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration centrale pour le signal de stress et l'enregistrement du ThrottlingService
 * en tant qu'observateur.
 */
@Configuration
public class StressConfiguration {

    @Bean
    public StressSignal stressSignal(ThrottlingService throttlingService) {
        StressSignal signal = new StressSignal();
        signal.addObserver(throttlingService);
        return signal;
    }
}

