package org.example.mealservice.domain.meal.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
public class MealRecordResponseDto {
    private Long mealRecordId;
    private LocalDate mealDate;
    private String mealTime;
    private String memo;
    private String mealPhotoUrl;
    private List<FoodItemQueryDto> foodItems;
}
