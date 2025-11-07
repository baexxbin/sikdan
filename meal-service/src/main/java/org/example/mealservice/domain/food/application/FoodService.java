package org.example.mealservice.domain.food.application;

import org.example.mealservice.domain.food.dto.request.FoodItemDto;

public interface FoodService {
    void updateFoodItem(Long mealRecordId, Long foodItemId, Long memberId, FoodItemDto FoodItemDto);
}
