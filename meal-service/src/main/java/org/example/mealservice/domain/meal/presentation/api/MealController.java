package org.example.mealservice.domain.meal.presentation.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mealservice.domain.meal.application.MealService;
import org.example.mealservice.domain.meal.dto.request.MealRecordCreateRequestDto;
import org.example.commonsecurity.auth.CustomUserDetails;
import org.example.mealservice.domain.meal.dto.response.MealRecordResponseDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
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

    @PostMapping("/record")
    public ResponseEntity<Long> createMeal(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody MealRecordCreateRequestDto request) {

        Long memberId = user.getMemberId();

        Long mealRecordId = mealService.createMealRecord(memberId, request);
        return ResponseEntity.ok(mealRecordId);
    }

    @PostMapping("/recordtest")
    public ResponseEntity<String> recordMeal(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader
    ) {
        System.out.println("Authorization header in meal-service = " + authHeader);
        return ResponseEntity.ok("meal recorded");
    }

    @GetMapping("my-meal")
    public ResponseEntity<?> getMyMeal(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        Long memberId = user.getMemberId();
        LocalDate targetDate = (date != null) ? date : LocalDate.now();

        List<MealRecordResponseDto> mealRecords = mealService.getMealRecordsByMemberIdAndDate(memberId, targetDate);

        return ResponseEntity.ok(mealRecords != null ? mealRecords : Collections.emptyList());
    }
}
