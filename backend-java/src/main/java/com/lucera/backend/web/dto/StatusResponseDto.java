package com.lucera.backend.web.dto;

public record StatusResponseDto(
        double stressLevel,
        boolean isThrottled,
        double energySaved,
        NutritionLabelDto etiquetteNutritionnelle
) {
}


