package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());


    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<Meal> getAll(int userId) {
        log.info("getAll (MealService)");
        return service.getAll(userId);
    }

    public List<Meal> getAll(int userId, LocalDate dateFrom, LocalDate dateTo) {
        log.info("getAll (MealService Overloaded)");
        return service.getAll(userId, dateFrom, dateTo);
    }


    public Meal get(int id, int userId) {
        log.info("get {}", id);
        Meal meal = null;
        meal = service.get(id, userId);
        return meal;
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal);
    }

    public void delete(int id, int userId) {
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        log.info("update {} ", meal.getId());
        service.update(meal);
    }


}