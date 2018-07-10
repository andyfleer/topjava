package ru.javawebinar.topjava.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Meal extends AbstractBaseEntity{
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Integer id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final int userId;

    public Meal(int userId, LocalDateTime dateTime, String description, int calories) {
        this(null, userId, dateTime, description, calories);
    }

    public Meal(Integer id, int userId, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

        public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
