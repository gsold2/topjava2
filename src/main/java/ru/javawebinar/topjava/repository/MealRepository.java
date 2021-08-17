package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    Meal addMeal(Meal meal);

    Meal getMeal(int id);

    void deleteMeal(int id);

    List<Meal> getAllMeals();
}
