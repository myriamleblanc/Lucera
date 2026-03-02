package com.lucera.backend.service;

import com.lucera.backend.domain.StressSignal;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simulateur de stress réseau.
 *
 * Met à jour le signal de stress toutes les 2 secondes (2000 ms) sans aucun verrou,
 * en utilisant un pattern très léger basé sur un ScheduledExecutor géré par Spring.
 */
@Component
public class StressSimulator {

    private final StressSignal stressSignal;

    // Compteur pour produire un pattern pseudo-périodique avec un bruit aléatoire.
    private final AtomicInteger tick = new AtomicInteger(0);

    public StressSimulator(StressSignal stressSignal) {
        this.stressSignal = stressSignal;
    }

    @PostConstruct
    public void init() {
        // Le scheduling est géré par l'annotation @Scheduled dans la méthode tick().
    }

    @org.springframework.scheduling.annotation.Scheduled(fixedRate = 2000)
    public void tick() {
        int t = tick.incrementAndGet();

        // Pattern de base : vague sinusoïdale lente entre 2 et 9
        double base = 5.5 + 3.5 * Math.sin(t / 5.0);

        // Bruit aléatoire léger pour simuler la variabilité du réseau
        double noise = ThreadLocalRandom.current().nextDouble(-1.5, 1.5);

        double level = base + noise;
        stressSignal.updateStress(level);
    }
}

