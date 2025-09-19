package org.example.mealservice.domain.meal.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.mealservice.domain.food.dto.request.FoodItemDto;
import org.example.mealservice.domain.meal.dto.request.MealRecordCreateRequestDto;
import org.example.mealservice.domain.meal.dto.response.MealRecordResponseDto;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface  MealMapper {

    void insertMealRecord(@Param("memberId") Long memberId, @Param("request") MealRecordCreateRequestDto request);

    void insertFoodItem(@Param("mealRecordId") Long mealRecordId, @Param("foodItems")List<FoodItemDto> foodItems);

    List<MealRecordResponseDto> selectMealRecordsByMemberIdAndDate(@Param("memberId") Long memberId, @Param("today") LocalDate today);

}
