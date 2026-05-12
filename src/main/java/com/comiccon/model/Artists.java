package com.comiccon.model;

/**
 * Художники
 */
public class Artists {
    private int artist_id;
    private String last_name;
    private String first_name;
    private String patronymic;

    public Artists() {}

    public Artists(int artist_id, String last_name, String patronymic, String first_name) {
        this.artist_id = artist_id;
        this.last_name = last_name;
        this.patronymic = patronymic;
        this.first_name = first_name;
    }

    public Artists(String last_name, String patronymic, String first_name) {
        this(0, last_name, first_name, patronymic);
    }

    public int getArtist_id() { return artist_id; }
    public void setArtist_id(int artist_id) { this.artist_id = artist_id; }
    public String getLast_name() { return last_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }
    public String getFirst_name() { return first_name; }
    public void setFirst_name(String first_name) { this.first_name = first_name; }
    public String getPatronymic() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }

    @Override
    public String toString() {
        return String.format("Художник{id=%d, '%s' '%s' '%s'}", artist_id, last_name, first_name, patronymic);
    }
}
