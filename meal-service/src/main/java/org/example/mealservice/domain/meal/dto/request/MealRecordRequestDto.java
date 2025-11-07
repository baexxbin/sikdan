package org.example.mealservice.domain.meal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.mealservice.domain.meal.model.MealTime;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealRecordRequestDto {
    private LocalDate mealDate;
    private MealTime mealTime;
    private String memo;
    private String mealPhotoUrl;
}
