package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        if (userId == repository.get(id).getUserId()) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.get(id).getUserId() == userId) {
            return repository.get(id);
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll in repository");
        List<Meal> list = new ArrayList<>();
        for (Map.Entry<Integer, Meal> entry:
                repository.entrySet()) {
            if (entry.getValue().getUserId() == userId) {
                list.add(entry.getValue());
            }
        }
        return list;
    }


    public Collection<Meal> getAll(int userId, LocalDate from, LocalDate to) {
        log.info("getAll in repository (overloaded");
        List<Meal> list = new ArrayList<>();
        for (Map.Entry<Integer, Meal> entry:
                repository.entrySet()) {
            if (entry.getValue().getUserId() == userId && DateTimeUtil.isBetweenDate(entry.getValue().getDate(), from, to)) {
                list.add(entry.getValue());
            }
        }
        return list;
    }
}

