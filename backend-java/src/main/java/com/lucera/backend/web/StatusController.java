package com.lucera.backend.web;

import com.lucera.backend.domain.StressSignal;
import com.lucera.backend.service.ThrottlingService;
import com.lucera.backend.web.dto.NutritionLabelDto;
import com.lucera.backend.web.dto.StatusResponseDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * StatusController exposant l'état du délestage du grid.
 *
 * GET /api/v1/status
 */
@RestController
@RequestMapping("/api/v1/status")
@CrossOrigin(origins = "http://localhost:5173")
public class StatusController {

    private final StressSignal stressSignal;
    private final ThrottlingService throttlingService;

    public StatusController(StressSignal stressSignal, ThrottlingService throttlingService) {
        this.stressSignal = stressSignal;
        this.throttlingService = throttlingService;
    }

    @GetMapping
    public StatusResponseDto getStatus() {
        double stress = stressSignal.getCurrentStress();

        boolean throttled = throttlingService.isThrottled();
        double energySavedKwh = throttlingService.getEnergySavedKwh();

        // Calcule l'étiquette nutritionnelle :
        // - Calories énergétiques : kWh économisés * facteur arbitraire
        // - Gras carbonés : grammes de CO2 évités
        // - Vitamines sociétales : indice de résilience pour la société
        double caloriesEnergetiques = energySavedKwh * 860.0; // 1 kWh ~ 860 kcal (approximation)

        NutritionLabelDto label = new NutritionLabelDto(
                caloriesEnergetiques,
                throttlingService.getCarbonFatGrams(),
                throttlingService.getSocietalVitaminsIndex()
        );

        return new StatusResponseDto(
                stress,
                throttled,
                energySavedKwh,
                label
        );
    }
}


