package com.comiccon.model;

/**
 * Жюри
 */
public class Jurys {
    private int juryId;
    private String lastName;
    private String firstName;
    private String patronymic;

    public Jurys() {}

    public Jurys(int juryId, String lastName, String firstName, String patronymic) {
        this.juryId = juryId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
    }

    public Jurys(String lastName, String firstName, String patronymic) {
        this(0, lastName, firstName, patronymic);
    }

    public int getJuryId() { return juryId; }
    public void setJuryId(int juryId) { this.juryId = juryId; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getPatronymic() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }

    @Override
    public String toString() {
        return String.format("Жюри{id=%d, '%s %s %s'}", juryId, lastName, firstName, patronymic);
    }
}
