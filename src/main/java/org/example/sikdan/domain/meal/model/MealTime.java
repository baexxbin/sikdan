package org.example.sikdan.domain.meal.model;

public enum MealTime {
    BREAKFAST("아침"),
    LUNCH("점심"),
    DINNER("저녁"),
    SNACK("간식"),
    LATE_NIGHT("야식");

    private final String label;

    MealTime(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
