package org.example.mealservice.domain.meal.model.vo;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.common.exception.CustomException;
import org.example.common.exception.ErrorCode;
import org.example.mealservice.domain.food.model.vo.FoodItem;
import org.example.mealservice.domain.meal.dto.request.MealUpdateDto;
import org.example.mealservice.domain.meal.model.MealTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealRecord {
    private Long mealRecordId;
    private Long memberId;
    private LocalDate mealDate;
    private MealTime mealTime;
    private String memo;
    private String mealPhotoUrl;
    private LocalDateTime createdAt;

    // 연관 객체
    private List<FoodItem> foodItems;

    public void update(MealUpdateDto dto) {
        this.mealDate = dto.getMealDate();
        this.mealTime = dto.getMealTime();
        this.memo = dto.getMemo();
        this.mealPhotoUrl = dto.getMealPhotoUrl();
    }

    public void validateOwner(Long memberId) {
        if (!this.memberId.equals(memberId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
    }
}
