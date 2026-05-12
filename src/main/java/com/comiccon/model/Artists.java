package com.comiccon.model;

/**
 * Художники
 */
public class Artists {
    private int artistId;
    private String lastName;
    private String firstName;
    private String patronymic;

    public Artists() {}

    public Artists(int artistId, String lastName, String firstName, String patronymic) {
        this.artistId = artistId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
    }

    public Artists(String lastName, String firstName, String patronymic) {
        this(0, lastName, firstName, patronymic);
    }

    public int getArtistId() { return artistId; }
    public void setArtistId(int artistId) { this.artistId = artistId; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getPatronymic() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }

    @Override
    public String toString() {
        return String.format("Художник{id=%d, '%s %s %s'}", artistId, lastName, firstName, patronymic);
    }
}
