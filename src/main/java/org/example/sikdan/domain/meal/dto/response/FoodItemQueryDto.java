package org.example.sikdan.domain.meal.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FoodItemQueryDto {
    private String foodName;
    private String amount;
    private String nutrientType;
    private String sourceType;
}
