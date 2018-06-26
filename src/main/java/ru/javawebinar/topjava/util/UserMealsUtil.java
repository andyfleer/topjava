package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );

        /*
         * two algorithms here
         */


        List<UserMealWithExceed> list;

        //getFilteredWithExceeded(mealList, LocalTime.of(10, 0), LocalTime.of(22,0), 2000);
        getFilteredWithExceeded_v2(mealList, LocalTime.of(7, 0), LocalTime.of(22,0), 2000);




    }


    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        /*
         *   BenchMark.ExecutionPlan.benchGetFilteredWithExceededByStream_v01             5  avgt   20   1771,896 ±  16,624  ns/op
         *   BenchMark.ExecutionPlan.benchGetFilteredWithExceededByStream_v01            10  avgt   20   3910,266 ± 139,203  ns/op
         *   BenchMark.ExecutionPlan.benchGetFilteredWithExceededByStream_v01            30  avgt   20  11908,832 ± 220,498  ns/op
         */
        List<UserMealWithExceed> resultList = new ArrayList<>();


        Map<LocalDate, List<UserMeal>> myMap = mealList.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate));

        for (Map.Entry<LocalDate, List<UserMeal>> entry :
                myMap.entrySet()) {
            final int[] sum = {0};

            sum[0] = entry.getValue().stream().map(UserMeal::getCalories).mapToInt(i -> i).sum();

            entry.getValue().stream()
                    .filter(e -> TimeUtil.isBetween(e.getDateTime().toLocalTime(), startTime, endTime ))
                    .forEach((e) -> resultList.add(new UserMealWithExceed(e.getDateTime(), e.getDescription(),
                            e.getCalories(), sum[0] > caloriesPerDay)));
        }

        return resultList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded_v2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        /*
         *   BenchMark.ExecutionPlan.benchGetFilteredWithExceededByStream_v2              5  avgt   20    870,499 ±  52,785  ns/op
         *   BenchMark.ExecutionPlan.benchGetFilteredWithExceededByStream_v2             10  avgt   20   1692,790 ±  66,547  ns/op
         *   BenchMark.ExecutionPlan.benchGetFilteredWithExceededByStream_v2             30  avgt   20   5366,569 ± 299,053  ns/op
         */

        List<UserMealWithExceed> resultList = new ArrayList<>();
        Map<LocalDate, Integer> caloriesMap =  new HashMap<>();

        for (UserMeal meal : mealList) {
                if (caloriesMap.containsKey(meal.getDate())) {
                        int calories = caloriesMap.get(meal.getDate());
                        calories += meal.getCalories();
                        caloriesMap.put(meal.getDate(), calories);
                }
                else
                {
                        caloriesMap.put(meal.getDate(), meal.getCalories());
                }
        }

        //==============================================

        for (UserMeal meal : mealList) {
                if (TimeUtil.isBetween(meal.getLocalTime(), startTime, endTime)) {
                        resultList.add(new UserMealWithExceed(
                                meal.getDateTime(),
                                meal.getDescription(),
                                meal.getCalories(),
                                caloriesMap.get(meal.getDate()) > caloriesPerDay));
                }
        }

        return resultList;
    }



}
