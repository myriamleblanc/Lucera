package com.lucera.backend.observer;

/**
 * Observer de niveau de stress réseau.
 * <p>
 * Utilisé par {@link com.lucera.backend.domain.StressSignal} pour notifier les
 * composants intéressés (services de délestage, métriques, etc.).
 */
@FunctionalInterface
public interface StressObserver {

    /**
     * Notification d'un nouveau niveau de stress.
     *
     * @param stressLevel niveau de stress du grid, entre 0 et 10
     */
    void update(double stressLevel);
}

