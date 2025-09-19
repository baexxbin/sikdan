package org.example.mealservice.domain.food.model;

public enum NutrientType {
    CARB("탄수화물"),
    PROTEIN("단백질"),
    FAT("지방"),
    VITAMIN("비타민"),
    MINERAL("무기질");

    private final String type;

    NutrientType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
