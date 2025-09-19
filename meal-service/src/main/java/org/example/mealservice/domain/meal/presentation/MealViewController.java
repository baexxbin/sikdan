package org.example.mealservice.domain.meal.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mealservice.domain.meal.application.MealService;
import org.example.mealservice.domain.meal.dto.response.MealRecordResponseDto;
import org.example.mealservice.security.CustomUserDetails;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MealViewController {
    private final MealService mealService;

    @GetMapping("/meal-record")
    public String showMealRecordPage(@AuthenticationPrincipal CustomUserDetails user, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                     Model model) {

        Long memberId = user.getMemberId();

        // 기본값 오늘, 그 외 날짜 지정
        LocalDate targetDate = (date != null) ? date : LocalDate.now();

        List<MealRecordResponseDto> mealRecords = mealService.getMealRecordsByMemberIdAndDate(memberId, targetDate);

        model.addAttribute("day", targetDate);
        model.addAttribute("mealRecords", mealRecords != null ? mealRecords : Collections.emptyList());

        return "meal-record";
    }
}
