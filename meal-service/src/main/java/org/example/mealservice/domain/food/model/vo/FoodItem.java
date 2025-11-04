package org.example.mealservice.domain.food.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.mealservice.domain.food.dto.request.FoodItemDto;
import org.example.mealservice.domain.food.model.NutrientType;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodItem {
    private Long foodItemId;
    private Long mealRecordId;
    private String foodName;
    private String amount;
    private NutrientType nutrientType;
    private String sourceType;
    private LocalDateTime createdAt;

    public void update(FoodItemDto dto) {
        foodName = dto.getFoodName();
        amount = dto.getAmount();
        nutrientType = dto.getNutrientType();
        sourceType = dto.getSourceType();
    }
}
