package org.example.mealservice.domain.meal.dto.response;

import lombok.*;
import org.example.mealservice.domain.food.dto.response.FoodItemResponseDto;
import org.example.mealservice.domain.meal.model.MealTime;
import org.example.mealservice.domain.meal.model.vo.MealRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealRecordResponseDto {
    private Long mealRecordId;
    private LocalDate mealDate;
    private MealTime mealTime;
    private String memo;
    private String mealPhotoUrl;
    private LocalDateTime createdAt;

    private List<FoodItemResponseDto> foodItems;

    public static MealRecordResponseDto from(MealRecord mealRecord) {
        return MealRecordResponseDto.builder()
                .mealRecordId(mealRecord.getMealRecordId())
                .mealDate(mealRecord.getMealDate())
                .mealTime(mealRecord.getMealTime())
                .memo(mealRecord.getMemo())
                .mealPhotoUrl(mealRecord.getMealPhotoUrl())
                .createdAt(mealRecord.getCreatedAt())
                .foodItems(mealRecord.getFoodItems() != null ?
                        mealRecord.getFoodItems().stream()
                                .map(FoodItemResponseDto::from)
                                .collect(Collectors.toList())
                        : List.of())
                .build();
    }
}
