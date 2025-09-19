package org.example.mealservice.domain.food.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.mealservice.domain.food.model.NutrientType;

@NoArgsConstructor
@Data
public class FoodItemDto {
    private String foodName;
    private String amount;
    private NutrientType nutrientType;
    private String sourceType;
}
