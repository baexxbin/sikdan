package org.example.sikdan.domain.meal.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.sikdan.domain.food.dto.request.FoodItemDto;
import org.example.sikdan.domain.meal.model.MealTime;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
public class MealRecordCreateRequestDto {
    private Long mealRecordId;  // insert 후 자동증가되는 시퀀스 값 받기 위해 필요
    private LocalDate mealDate;
    private MealTime mealTime;
    private String memo;
    private String mealPhotoUrl;
    private List<FoodItemDto> foodItems;

}
