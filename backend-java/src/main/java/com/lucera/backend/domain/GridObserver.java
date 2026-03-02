package com.lucera.backend.domain;

/**
 * GridObserver réagit aux changements de niveau de stress du réseau (0-10).
 */
@FunctionalInterface
public interface GridObserver {

    /**
     * Notifié à chaque changement significatif de stress du grid.
     *
     * @param newLevel nouveau niveau de stress (0-10)
     */
    void onStressLevelChanged(double newLevel);
}

