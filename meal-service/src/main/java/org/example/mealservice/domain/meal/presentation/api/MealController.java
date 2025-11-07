package org.example.mealservice.domain.meal.presentation.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mealservice.domain.food.application.FoodService;
import org.example.mealservice.domain.meal.application.MealService;
import org.example.mealservice.domain.meal.dto.request.MealRecordCreateRequestDto;
import org.example.commonsecurity.auth.CustomUserDetails;
import org.example.mealservice.domain.meal.dto.request.MealUpdateDto;
import org.example.mealservice.domain.meal.dto.response.MealIdResponseDto;
import org.example.mealservice.domain.meal.dto.response.MealRecordResponseDto;
import org.example.mealservice.domain.meal.model.MealTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
@Slf4j
public class MealController {

    private final MealService mealService;
    private final FoodService foodService;

    @PostMapping("/record")
    public ResponseEntity<Long> createMeal(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody MealRecordCreateRequestDto request) {

        Long memberId = user.getMemberId();

        Long mealRecordId = mealService.createMealRecord(memberId, request);
        return ResponseEntity.ok(mealRecordId);
    }


    @GetMapping("my-meal")
    public ResponseEntity<?> getMyMeal(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        Long memberId = user.getMemberId();
        LocalDate targetDate = (date != null) ? date : LocalDate.now();

        List<MealRecordResponseDto> mealRecords = mealService.getDayMealRecords(memberId, targetDate);

        return ResponseEntity.ok(mealRecords != null ? mealRecords : Collections.emptyList());
    }

    // 날짜, mealTime으로 mealRecord조회
    @GetMapping("/record-id")
    public ResponseEntity<MealIdResponseDto> getMealRecordById(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam MealTime mealTime) {
        Long memberId = userDetails.getMemberId();
        Long mealRecordId = mealService.getMealId(memberId, date, mealTime);

        return ResponseEntity.ok(new MealIdResponseDto(mealRecordId));
    }

    // 특정 식단 상세 조회
    @GetMapping("/{mealRecordId}")
    public ResponseEntity<MealRecordResponseDto> getMealRecord(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long mealRecordId

    ) {
        Long memberId = userDetails.getMemberId();
        MealRecordResponseDto meal = mealService.getMealRecordById(memberId, mealRecordId);
        return ResponseEntity.ok(meal);
    }


    // 식단 수정
    @PutMapping("/{mealRecordId}")
    public ResponseEntity<MealRecordResponseDto> updateMeal(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long mealRecordId,
            @RequestBody MealUpdateDto request
    ) {
        Long memberId = userDetails.getMemberId();
        MealRecordResponseDto updated = mealService.updateMealRecord(mealRecordId, memberId, request);

        return ResponseEntity.ok(updated);
    }

    // 식단 삭제
    @DeleteMapping("/{mealRecordId}")
    public ResponseEntity<Void> deleteMeal(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long mealRecordId
    ) {
        Long memberId = userDetails.getMemberId();
        mealService.deleteMealRecord(memberId, mealRecordId);
        return ResponseEntity.noContent().build();
    }
}
