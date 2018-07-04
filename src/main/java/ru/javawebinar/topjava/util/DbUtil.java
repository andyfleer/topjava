package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class DbUtil {
    private static ConcurrentHashMap<Integer, Meal> meals = new ConcurrentHashMap<>();
    private static AtomicInteger maxIndex = new AtomicInteger(0);

    static {
        meals.put(maxIndex.intValue(), new Meal(maxIndex.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        meals.put(maxIndex.intValue(), new Meal(maxIndex.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        meals.put(maxIndex.intValue(), new Meal(maxIndex.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        meals.put(maxIndex.intValue(), new Meal(maxIndex.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        meals.put(maxIndex.intValue(), new Meal(maxIndex.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        meals.put(maxIndex.intValue(), new Meal(maxIndex.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public static List<Meal> getData() {
        List<Meal> list = new ArrayList<>();
        for (Map.Entry<Integer, Meal> integerMealEntry : meals.entrySet()) {
            list.add(integerMealEntry.getValue());
        }
        return list;
    }

    public static void add(Meal meal) {
        meals.put(maxIndex.getAndIncrement(), meal);
    }

    public static void delete(Integer id) {
        meals.remove(id);
    }

    public static void update(Integer id,  Meal meal) throws Exception {
        if(id.equals(meal.getId())) {
            meals.put(id, meal);
        }
        else
        {
            throw new Exception("Id mismatch");
        }
    }

    public static Meal get(Integer id) {
        return meals.get(id);
    }

    public static int getCurrentIndex() {
        return maxIndex.intValue();
    }
}
