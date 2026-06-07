package com.comiccon.model;

/**
 * Волонтеры
 */
public class Volunteers {
    private int volunteerId;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String task;

    public Volunteers() {}

    public Volunteers(int volunteerId, String lastName, String firstName, String patronymic, String task) {
        this.volunteerId = volunteerId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.task = task;
    }

    public Volunteers(String lastName, String firstName, String patronymic, String task) {
        this(0, lastName, firstName, patronymic, task);
    }

    public int getVolunteerId() { return volunteerId; }
    public void setVolunteerId(int volunteerId) { this.volunteerId = volunteerId; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getPatronymic() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }
    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

    @Override
    public String toString() {
        return String.format("Волонтер{id=%d, '%s %s %s', task: '%s'}", volunteerId, lastName, firstName, patronymic, task);
    }
}
