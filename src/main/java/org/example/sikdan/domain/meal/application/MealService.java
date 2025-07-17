package org.example.sikdan.domain.meal.application;

import org.example.sikdan.domain.meal.dto.request.MealRecordCreateRequestDto;
import org.example.sikdan.domain.meal.dto.response.MealRecordResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface MealService {
    Long createMealRecord(MealRecordCreateRequestDto request);

    List<MealRecordResponseDto> getMealRecordsByMemberIdAndDate(Long memberId, LocalDate date);
}
