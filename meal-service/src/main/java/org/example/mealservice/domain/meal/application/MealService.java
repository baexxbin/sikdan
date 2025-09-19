package org.example.mealservice.domain.meal.application;

import org.example.mealservice.domain.meal.dto.request.MealRecordCreateRequestDto;
import org.example.mealservice.domain.meal.dto.response.MealRecordResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface MealService {
    Long createMealRecord(Long memberId, MealRecordCreateRequestDto request);

    List<MealRecordResponseDto> getMealRecordsByMemberIdAndDate(Long memberId, LocalDate date);


}
