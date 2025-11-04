package org.example.mealservice.domain.food.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.mealservice.domain.food.model.vo.FoodItem;

@Mapper
public interface FoodMapper {

    FoodItem findById(@Param("foodItemId") Long foodItemId);

    void updateFoodItem(@Param("foodItem") FoodItem foodItem);

    boolean existsByIdAndMealId(@Param("foodItemId") Long foodItemId, @Param("mealRecordId") Long mealRecordId);
}
