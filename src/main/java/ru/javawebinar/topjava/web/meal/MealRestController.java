package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("create with userId={}", userId);
        checkNew(meal);
        return service.create(userId, meal);
    }

    public void update(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("update {} with userId={}", meal, userId);
        service.update(userId, meal);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete {} with userId={}", id, userId);
        service.delete(userId, id);
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get {} with userId={}", id, userId);
        return service.get(userId, id);
    }

    public List<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll with userId={}", userId);
        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getBetweenDatesOrDefault(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetweenDatesOrDefault with userId={}", userId);
        List<Meal> meals = service.getBetweenDates(userId, startDate == null ? LocalDate.MIN : startDate,
                endDate == null ? LocalDate.MAX : endDate);
        return MealsUtil.getFilteredTos(meals, SecurityUtil.authUserCaloriesPerDay(),
                startTime == null ? LocalTime.MIN : startTime,
                endTime == null ? LocalTime.MAX : endTime);
    }
}