package com.comiccon.model;

/**
 * Участники
 */
public class Members {
    private int memberId;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String hero;
    private String originalSource;

    public Members() {}

    public Members(int memberId, String lastName, String firstName, String patronymic, String hero, String originalSource) {
        this.memberId = memberId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.hero = hero;
        this.originalSource = originalSource;
    }

    public Members(String lastName, String firstName, String patronymic, String hero, String originalSource) {
        this(0, lastName, firstName, patronymic, hero, originalSource);
    }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getPatronymic() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }
    public String getHero() { return hero; }
    public void setHero(String hero) { this.hero = hero; }
    public String getOriginalSource() { return originalSource; }
    public void setOriginalSource(String originalSource) { this.originalSource = originalSource; }

    @Override
    public String toString() {
        return String.format("Участник{id=%d, '%s %s %s', hero='%s', original_source='%s' }", memberId, lastName, firstName, patronymic, hero, originalSource);
    }

}
