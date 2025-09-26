package org.example.mealservice.domain.meal.presentation.api;

import lombok.RequiredArgsConstructor;
import org.example.mealservice.domain.meal.application.MealService;
import org.example.mealservice.domain.meal.dto.request.MealRecordCreateRequestDto;
import org.example.commonsecurity.auth.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @PostMapping("/record")
    public ResponseEntity<Long> createMeal(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody MealRecordCreateRequestDto request) {

        Long memberId = user.getMemberId();

        Long mealRecordId = mealService.createMealRecord(memberId, request);
        return ResponseEntity.ok(mealRecordId);
    }
}
