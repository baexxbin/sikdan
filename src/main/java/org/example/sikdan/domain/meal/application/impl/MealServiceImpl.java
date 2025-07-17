package org.example.sikdan.domain.meal.application.impl;

import lombok.RequiredArgsConstructor;
import org.example.sikdan.domain.meal.dto.request.MealRecordCreateRequestDto;
import org.example.sikdan.domain.meal.dto.response.MealRecordResponseDto;
import org.example.sikdan.domain.meal.persistence.MealMapper;
import org.example.sikdan.domain.meal.application.MealService;
import org.example.sikdan.util.InputSanitizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealMapper mealMapper;

    @Override
    @Transactional
    public Long createMealRecord(MealRecordCreateRequestDto requestDto) {
        // 0. 입력값 전처리 null
        sanitizeRequestDto(requestDto);

        // 1. meal_record 테이블에 insert (mealRecordId 자동으로 채움)
        mealMapper.insertMealRecord(requestDto);
        Long mealRecordId = requestDto.getMealRecordId();

        // 2. food_item 테이블에 연관된 음식 insert
        if (requestDto.getFoodItems() != null && !requestDto.getFoodItems().isEmpty()) {
            mealMapper.insertFoodItem(mealRecordId, requestDto.getFoodItems());
        }

        return mealRecordId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MealRecordResponseDto> getMealRecordsByMemberIdAndDate(Long memberId, LocalDate date) {
        return mealMapper.selectMealRecordsByMemberIdAndDate(memberId, date);
    }

    // MealRecordCreateRequestDto 전처리
    private void sanitizeRequestDto(MealRecordCreateRequestDto requestDto) {
        if (requestDto == null) return;

        requestDto.setMemo(InputSanitizer.nullIfBlank(requestDto.getMemo()));
        requestDto.setMealPhotoUrl(InputSanitizer.nullIfBlank(requestDto.getMealPhotoUrl()));
    }
}
