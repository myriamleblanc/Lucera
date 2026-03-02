package com.lucera.backend.service;

import com.google.common.util.concurrent.AtomicDouble;
import com.lucera.backend.observer.StressObserver;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Service de délestage ultra-léger, thread-safe sans verrous.
 *
 * - Décide si le système est en mode throttling en fonction du niveau de stress (0-10).
 * - Simule un bridage de 10% de la charge de calcul lorsque le stress > 8.
 * - Accumule une estimation de l'énergie économisée.
 */
@Service
public class ThrottlingService implements StressObserver {

    private final AtomicBoolean throttled = new AtomicBoolean(false);
    private final AtomicDouble energySavedKwh = new AtomicDouble(0.0);

    // Charge de calcul simulée : 1.0 = 100% (nominal), 0.9 = 90% (délestage 10%).
    private final AtomicDouble simulatedComputeLoadFactor = new AtomicDouble(1.0);

    // Quelques métriques additionnelles dérivées, sans verrou
    private final AtomicDouble carbonFatGrams = new AtomicDouble(0.0);
    private final AtomicDouble societalVitaminsIndex = new AtomicDouble(0.0);

    // Permet de dériver des vitesses d'évolution
    private volatile Instant lastUpdate = Instant.now();

    @Override
    public void update(double stressLevel) {
        // Règle métier : au-delà de 8.0, on active le délestage (réduction de 10%).
        boolean shouldThrottle = stressLevel > 8.0;
        throttled.set(shouldThrottle);
        simulatedComputeLoadFactor.set(shouldThrottle ? 0.9 : 1.0);

        Instant now = Instant.now();
        long millisSinceLast = Math.max(1, now.toEpochMilli() - lastUpdate.toEpochMilli());
        lastUpdate = now;

        // Signal de stress normalisé [0-1]
        double factor = stressLevel / 10.0;
        double seconds = millisSinceLast / 1000.0;

        // Modèle : la consommation "baseline" suit le niveau de stress.
        // Le délestage retire 10% de cette consommation.
        double baselineLoad = factor;
        double reductionFraction = shouldThrottle ? 0.10 : 0.0;

        // Énergie économisée (kWh) ~ fraction réduite * charge * temps
        double deltaEnergy = reductionFraction * baselineLoad * seconds;
        energySavedKwh.addAndGet(deltaEnergy);

        // Gras carbonés (g CO2 évités) : proportionnel à l'énergie économisée.
        double deltaCarbon = deltaEnergy * 0.8;
        carbonFatGrams.addAndGet(deltaCarbon);

        // Vitamines sociétales : indicateur arbitraire [0-100].
        double targetVitamins = shouldThrottle ? 80.0 * factor : 40.0 * factor;
        societalVitaminsIndex.set(Math.min(100.0, targetVitamins));
    }

    public boolean isThrottled() {
        return throttled.get();
    }

    public double getEnergySavedKwh() {
        return energySavedKwh.get();
    }

    public double getCarbonFatGrams() {
        return carbonFatGrams.get();
    }

    public double getSocietalVitaminsIndex() {
        return societalVitaminsIndex.get();
    }

    public double getSimulatedComputeLoadFactor() {
        return simulatedComputeLoadFactor.get();
    }
}



