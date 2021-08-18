package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealStorageInMemory;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final int CALORIES_PER_DAY = 2000;
    private static final Logger log = getLogger(MealServlet.class);

    private final MealRepository mealRepository = new MealStorageInMemory();

    {
        MealsUtil.getMeals().forEach(mealRepository::addMeal);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            switch (action) {
                case "update":
                    log.debug("forward to /mealForm.jsp");
                    Meal meal = mealRepository.getMeal(id);
                    request.setAttribute("dateTime", meal.getDateTime());
                    request.setAttribute("description", meal.getDescription());
                    request.setAttribute("calories", meal.getCalories());
                    request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                    break;
                case "delete":
                    mealRepository.deleteMeal(id);
                    response.sendRedirect("meals");
                    return;
                default:
                    break;
            }
        }

        log.debug("forward to meals");
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(mealRepository.getAllMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        request.setAttribute("mealsTo", mealsTo);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        request.setCharacterEncoding("UTF-8");
        Integer id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : null;
        LocalDateTime dataTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        mealRepository.addMeal(new Meal(id, dataTime, description, calories));
        response.sendRedirect("meals");
    }
}
