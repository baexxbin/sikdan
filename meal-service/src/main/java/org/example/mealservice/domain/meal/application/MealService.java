package org.example.mealservice.domain.meal.application;

import org.example.mealservice.domain.meal.dto.request.MealRecordCreateRequestDto;
import org.example.mealservice.domain.meal.dto.request.MealUpdateDto;
import org.example.mealservice.domain.meal.dto.response.MealRecordResponseDto;
import org.example.mealservice.domain.meal.model.MealTime;

import java.time.LocalDate;
import java.util.List;

public interface MealService {
    Long createMealRecord(Long memberId, MealRecordCreateRequestDto request);

    List<MealRecordResponseDto> getDayMealRecords(Long memberId, LocalDate date);

    Long getMealId(Long memberId, LocalDate date, MealTime mealTime);

    MealRecordResponseDto getMealRecordById(Long memberId, Long id);

    MealRecordResponseDto updateMealRecord(Long mealRecordId, Long memberId, MealUpdateDto request);

    boolean deleteMealRecord(Long memberId, Long id);

}
