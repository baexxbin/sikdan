package org.example.sikdan.domain.food.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.sikdan.domain.food.model.NutrientType;

@NoArgsConstructor
@Data
public class FoodItemDto {
    private String foodName;
    private String amount;
    private NutrientType nutrientType;
    private String sourceType;
}
