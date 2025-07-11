package org.example.sikdan.domain.meal.application;

import org.example.sikdan.domain.meal.dto.request.MealRecordCreateRequestDto;

public interface MealService {
    Long createMealRecord(MealRecordCreateRequestDto request);
}
