package com.lucera.backend.domain;

import com.google.common.util.concurrent.AtomicDouble;
import com.lucera.backend.observer.StressObserver;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Sujet (Observable) qui maintient un niveau de stress du grid et notifie les observateurs.
 *
 * Thread-safe : basé sur AtomicDouble + CopyOnWriteArrayList, aucun verrou explicite.
 */
public class StressSignal {

    private final AtomicDouble currentStress = new AtomicDouble(0.0);
    private final List<StressObserver> observers = new CopyOnWriteArrayList<>();

    public double getCurrentStress() {
        return currentStress.get();
    }

    public void addObserver(StressObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(StressObserver observer) {
        observers.remove(observer);
    }

    /**
     * Met à jour le stress et notifie les observateurs si le changement est significatif.
     */
    public void updateStress(double newLevel) {
        if (newLevel < 0.0) {
            newLevel = 0.0;
        } else if (newLevel > 10.0) {
            newLevel = 10.0;
        }

        double previous = currentStress.getAndSet(newLevel);

        // Seuil minimal pour éviter les notifications inutiles
        if (Math.abs(previous - newLevel) < 0.05) {
            return;
        }

        for (StressObserver observer : observers) {
            observer.update(newLevel);
        }
    }
}

