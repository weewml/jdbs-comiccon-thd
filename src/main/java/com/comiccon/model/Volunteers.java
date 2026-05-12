package com.comiccon.model;

/**
 * Волонтеры
 */
public class Volunteers {
    private int volunteer_id;
    private String last_name;
    private String first_name;
    private String patronymic;
    private String task;

    public Volunteers() {}

    public Volunteers(int volunteer_id, String last_name, String patronymic, String first_name, String task) {
        this.volunteer_id = volunteer_id;
        this.last_name = last_name;
        this.patronymic = patronymic;
        this.first_name = first_name;
        this.task = task;
    }

    public Volunteers(String last_name, String patronymic, String first_name, String task) {
        this(0, last_name, first_name, patronymic, task);
    }

    public int getVolunteer_id() { return volunteer_id; }
    public void setVolunteer_id(int volunteer_id) { this.volunteer_id = volunteer_id; }
    public String getLast_name() { return last_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }
    public String getFirst_name() { return first_name; }
    public void setFirst_name(String first_name) { this.first_name = first_name; }
    public String getPatronymic() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }
    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

    @Override
    public String toString() {
        return String.format("Волонтер{id=%d, '%s %s %s', task: '%s'}", volunteer_id, last_name, first_name, patronymic);
    }
}
