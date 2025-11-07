package org.example.mealservice.domain.meal.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.mealservice.domain.food.dto.request.FoodItemDto;
import org.example.mealservice.domain.meal.dto.request.MealRecordCreateRequestDto;
import org.example.mealservice.domain.meal.dto.request.MealUpdateDto;
import org.example.mealservice.domain.meal.dto.response.MealRecordResponseDto;
import org.example.mealservice.domain.meal.model.MealTime;
import org.example.mealservice.domain.meal.model.vo.MealRecord;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface  MealMapper {

    void insertMealRecord(@Param("memberId") Long memberId, @Param("request") MealRecordCreateRequestDto request);

    void insertFoodItem(@Param("mealRecordId") Long mealRecordId, @Param("foodItems")List<FoodItemDto> foodItems);

    List<MealRecord> selectMealRecordsByMemberIdAndDate(@Param("memberId") Long memberId, @Param("today") LocalDate today);

    boolean existsByIdAndMemberId(@Param("mealRecordId") Long mealRecordId, @Param("memberId") Long memberId);

    MealRecord findMealRecordById(@Param("memberId") Long memberId, @Param("mealRecordId") Long mealRecordId);

    int updateMeal(@Param("mealRecordId") Long mealRecordId, @Param("memberId") Long memberId, @Param("mealRecord") MealUpdateDto mealUpdateDto);

    int deleteMeal(@Param("mealRecordId") Long mealRecordId, @Param("memberId") Long memberId);

    Long findMealId(@Param("memberId") Long memberId, @Param("mealDate") LocalDate mealDate, @Param("mealTime") MealTime mealTime);
}
