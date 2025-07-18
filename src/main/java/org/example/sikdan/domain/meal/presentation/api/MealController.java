package org.example.sikdan.domain.meal.presentation.api;

import lombok.RequiredArgsConstructor;
import org.example.sikdan.domain.meal.application.MealService;
import org.example.sikdan.domain.meal.dto.request.MealRecordCreateRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @PostMapping("record")
    public ResponseEntity<Long> createMeal(@RequestBody MealRecordCreateRequestDto request) {
        /* TODO: 인증 구현 전까진 memberId를 request에서 직접 받음
            구현 후엔 Authentication 객체에서 꺼내쓰기

            Long memberId = getAuthenticatedMemberId(); // JWT나 세션에서 추출
            requestDto.setMemberId(memberId); // 여기에 주입
        * */

        Long mealRecordId = mealService.createMealRecord(request);
        return ResponseEntity.ok(mealRecordId);
    }
}
