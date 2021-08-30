package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private final Map<Integer, Map<Integer, Meal>> repository = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(1, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        if (mealMap == null) {
            meal.setId(counter.incrementAndGet());
            Map<Integer, Meal> map = new ConcurrentHashMap<>();
            Integer mealId = meal.getId();
            log.info("save {} with userId={}", mealId, userId);
            repository.put(userId, map);
            return map.putIfAbsent(mealId, meal);
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        Integer mealId = meal.getId();
        log.info("save {} with userId={}", mealId, userId);
        return mealMap.compute(mealId, (key, value) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete {} with userId={}", id, userId);
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap != null && mealMap.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get {} with userId={}", id, userId);
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap != null ? mealMap.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll with userId={}", userId);
        return filterByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetweenDates(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getBetweenDates userId {}", userId);
        return filterByPredicate(userId, meal -> (meal.getDate().compareTo(startDate) >= 0) & (meal.getDate().compareTo(endDate) <= 0));
    }

    private List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        if (mealMap != null) {
            return mealMap.values().stream()
                    .filter(filter)
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}



