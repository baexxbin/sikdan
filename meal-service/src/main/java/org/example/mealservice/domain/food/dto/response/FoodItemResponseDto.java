package org.example.mealservice.domain.food.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.mealservice.domain.food.model.vo.FoodItem;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodItemResponseDto {
    private Long foodItemId;
    private String foodName;
    private String nutrientType;
    private String amount;
    private String sourceType;
    private LocalDateTime createdAt;

    public static FoodItemResponseDto from(FoodItem foodItem) {
        return FoodItemResponseDto.builder()
                .foodItemId(foodItem.getFoodItemId())
                .foodName(foodItem.getFoodName())
                .nutrientType(foodItem.getNutrientType().name())
                .amount(foodItem.getAmount())
                .sourceType(foodItem.getSourceType())
                .createdAt(foodItem.getCreatedAt())
                .build();
    }
}
