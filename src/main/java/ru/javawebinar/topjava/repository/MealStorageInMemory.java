package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealStorageInMemory implements MealRepository {
    private static final Logger log = getLogger(MealStorageInMemory.class);
    private final AtomicInteger id = new AtomicInteger(0);
    private final ConcurrentMap<Integer, Meal> repository = new ConcurrentHashMap<>();

    @Override
    public Meal addMeal(Meal meal) {
        if (meal.getId() == null) {
            int id = this.id.incrementAndGet();
            log.debug("save new meal with id={}", id);
            meal.setId(id);
            repository.putIfAbsent(id, meal);
            return meal;
        } else if (repository.get(meal.getId()) != null) {
            log.debug("update meal with id={}", meal.getId());
            repository.replace(meal.getId(), meal);
            return meal;
        }
        return meal;
    }

    @Override
    public Meal getMeal(int id) {
        log.debug("get meal with id={}", id);
        return repository.get(id);
    }

    @Override
    public void deleteMeal(int id) {
        log.debug("delete meal with id={}", id);
        repository.remove(id);
    }

    @Override
    public List<Meal> getAllMeals() {
        log.debug("get all meals");
        return new ArrayList<>(repository.values());
    }
}
