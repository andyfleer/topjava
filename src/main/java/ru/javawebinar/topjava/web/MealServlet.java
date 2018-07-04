package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.DbUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.*;
import java.time.format.*;
import java.util.List;

public class MealServlet extends HttpServlet {
    private List<Meal> mealsList;

    @Override
    public void init() throws ServletException {
        super.init();
        mealsList = DbUtil.getData();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            String theCommand = request.getParameter("command");

            if (theCommand == null) {
                theCommand = "LIST";
            }

            switch (theCommand) {
                case "LIST":
                    listMeals(request, response);
                    break;
                case "ADD":
                    addMeal(request,response);
                    break;
                case "UPDATE":
                    updateMeal(request, response);
                    break;
                default:
                    listMeals(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            String theCommand = request.getParameter("command");

            if (theCommand == null) {
                theCommand = "LIST";
            }

            switch (theCommand) {
                case "LOAD":
                    loadMealUpdateForm(request, response);
                    break;
                case "DELETE":
                    deleteMeal(request, response);
                    break;
                default:
                    listMeals(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer mealId = Integer.parseInt(request.getParameter("mealId"));
        DbUtil.delete(mealId);

        listMeals(request, response);
    }

    private void loadMealUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer mealId = Integer.parseInt(request.getParameter("mealId"));
        Meal meal = DbUtil.get(mealId);
        request.setAttribute("MEAL", meal);
        request.getRequestDispatcher("update-meal-form.jsp").forward(request,response);
    }

    private void updateMeal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = Integer.parseInt(request.getParameter("mealId"));

        Integer calories = Integer.parseInt(request.getParameter("calories"));
        String description = request.getParameter("description");

        String str = request.getParameter("date") + " " + request.getParameter("time");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        Meal meal = new Meal(id, dateTime, description, calories);

        DbUtil.update(id, meal);

        listMeals(request, response);
    }


    private void addMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String calories = request.getParameter("calories");
            String description = request.getParameter("description");

            String str = request.getParameter("date") + " " + request.getParameter("time");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

            DbUtil.add(new Meal(DbUtil.getCurrentIndex(),  dateTime, description, Integer.parseInt(calories)));
        }
        catch (Exception e) {}
        finally {
            listMeals(request, response);
        }
    }

    private void updateData() {
        mealsList = DbUtil.getData();
    }


    private void listMeals(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateData();
        List<MealWithExceed> mealsWithExceeds = MealsUtil.getFilteredWithExceeded(mealsList, LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("mealsWithExceed", mealsWithExceeds);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
