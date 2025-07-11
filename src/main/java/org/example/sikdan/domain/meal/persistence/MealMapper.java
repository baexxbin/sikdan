package org.example.sikdan.domain.meal.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.sikdan.domain.food.dto.request.FoodItemDto;
import org.example.sikdan.domain.meal.dto.request.MealRecordCreateRequestDto;

import java.util.List;

@Mapper
public interface  MealMapper {

    void insertMealRecord(MealRecordCreateRequestDto request);

    void insertFoodItem(@Param("mealRecordId") Long mealRecordId, @Param("foodItems")List<FoodItemDto> foodItems);
}
