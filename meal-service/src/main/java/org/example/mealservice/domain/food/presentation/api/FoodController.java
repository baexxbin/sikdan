package org.example.mealservice.domain.food.presentation.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commonsecurity.auth.CustomUserDetails;
import org.example.mealservice.domain.food.application.FoodService;
import org.example.mealservice.domain.food.dto.request.FoodItemDto;
import org.example.mealservice.domain.meal.application.MealService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
@Slf4j
public class FoodController {

    private final MealService mealService;
    private final FoodService foodService;

    // 식단 수정
    @PutMapping("/{mealRecordId}/food/{foodItemId}")
    public ResponseEntity<FoodItemDto> updateFoodItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long mealRecordId,
            @PathVariable Long foodItemId,
            @RequestBody FoodItemDto foodItemDto
    ) {
        Long memberId = userDetails.getMemberId();
        foodService.updateFoodItem(mealRecordId, foodItemId, memberId, foodItemDto);
        return ResponseEntity.noContent().build();      // 204 No Content
    }
}
