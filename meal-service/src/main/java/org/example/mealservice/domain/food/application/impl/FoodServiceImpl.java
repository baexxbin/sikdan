package org.example.mealservice.domain.food.application.impl;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.mealservice.domain.food.application.FoodService;
import org.example.mealservice.domain.food.dto.request.FoodItemDto;
import org.example.mealservice.domain.food.model.vo.FoodItem;
import org.example.mealservice.domain.food.persistence.FoodMapper;
import org.example.mealservice.domain.meal.persistence.MealMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodMapper foodMapper;
    private final MealMapper mealMapper;

    @Override
    @Transactional
    public void updateFoodItem(Long mealRecordId, Long foodItemId, Long memberId, FoodItemDto foodItemDto) {
        // 식단 존재 확인 （사용자id＋식단id)
        boolean isMeal = mealMapper.existsByIdAndMemberId(mealRecordId, memberId);
        if (!isMeal) {
            throw new NotFoundException("Meal not found: " + mealRecordId);
        }

        // 음식 조회
        FoodItem foodItem = foodMapper.findById(foodItemId);
        if (foodItem == null) throw new NotFoundException("Food item not found: " + foodItemId);

        // mealRecord 일치 여부 확인
        if (!foodItem.getMealRecordId().equals(mealRecordId)) {
            throw new NotFoundException("Food item does not belong to this meal: " + foodItemId);
        }

        // 식단 수정
        foodItem.update(foodItemDto);

        // DB반영
        foodMapper.updateFoodItem(foodItem);
    }
}
