package org.example.mealservice.domain.meal.application.impl;

import lombok.RequiredArgsConstructor;
import org.example.mealservice.domain.meal.dto.request.MealRecordCreateRequestDto;
import org.example.mealservice.domain.meal.dto.response.MealRecordResponseDto;
import org.example.mealservice.domain.meal.persistence.MealMapper;
import org.example.mealservice.domain.meal.application.MealService;
import org.example.mealservice.infrastructure.client.MemberServiceClient;
import org.example.mealservice.util.InputSanitizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealMapper mealMapper;
    private final MemberServiceClient memberServiceClient; // Feign

    @Override
    @Transactional
    public Long createMealRecord(Long memberId, MealRecordCreateRequestDto requestDto) {
        // 1. memberId 검증
        Boolean exists = memberServiceClient.existsById(memberId);
        if (exists == null || !exists) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다: " + memberId);
        }

        // 2. 입력값 전처리 null
        sanitizeRequestDto(requestDto);

        // 3. meal_record 테이블에 insert (mealRecordId 자동으로 채움)
        mealMapper.insertMealRecord(memberId, requestDto);
        Long mealRecordId = requestDto.getMealRecordId();

        // 4. food_item 테이블에 연관된 음식 insert
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
