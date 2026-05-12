package com.comiccon.model;

/**
 * Жюри
 */
public class Jurys {
    private int jury_id;
    private String last_name;
    private String first_name;
    private String patronymic;

    public Jurys() {}

    public Jurys(int jury_id, String last_name, String patronymic, String first_name) {
        this.jury_id = jury_id;
        this.last_name = last_name;
        this.patronymic = patronymic;
        this.first_name = first_name;
    }

    public Jurys(String last_name, String patronymic, String first_name) {
        this(0, last_name, first_name, patronymic);
    }

    public int getJury_id() { return jury_id; }
    public void setJury_id(int jury_id) { this.jury_id = jury_id; }
    public String getLast_name() { return last_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }
    public String getFirst_name() { return first_name; }
    public void setFirst_name(String first_name) { this.first_name = first_name; }
    public String getPatronymic() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }

    @Override
    public String toString() {
        return String.format("Жюри{id=%d, '%s' '%s' '%s'}", jury_id, last_name, first_name, patronymic);
    }
}
