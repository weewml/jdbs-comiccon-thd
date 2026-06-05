package com.comiccon.model;

import java.time.LocalDate;

/**
 * Фестиваль.
 */
public class Fest {
    private int eventId;
    private int companyId;
    private int performanceId;
    private int volunteerId;
    private String address;
    private LocalDate date;

    // доп поля для JOIN запросов
    private String companyName;
    private String performanceTopic;
    private String volunteerLastName;
    private String volunteerTask;

    public Fest() { }

    public Fest(int eventId, int companyId, int performanceId, int volunteerId, String address, LocalDate date) {
        this.eventId = eventId;
        this.companyId = companyId;
        this.performanceId = performanceId;
        this.volunteerId = volunteerId;
        this.address = address;
        this.date = date;
    }

    public Fest(int companyId, int performanceId, int volunteerId, String address, LocalDate date) {
        this(0, companyId, performanceId, volunteerId, address, date);
    }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }
    public int getPerformanceId() { return performanceId; }
    public void setPerformanceId(int performanceId) { this.performanceId = performanceId; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public int getVolunteerId() { return volunteerId; }
    public void setVolunteerId(int volunteerId) { this.volunteerId = volunteerId; }

    // геттеры/сеттеры для JOIN
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getPerformanceTopic() { return performanceTopic; }
    public void setPerformanceTopic(String performanceTopic) { this.performanceTopic = performanceTopic; }
    public String getVolunteerLastName() { return volunteerLastName; }
    public void setVolunteerLastName(String volunteerLastName) { this.volunteerLastName = volunteerLastName; }
    public String getVolunteerTask() { return volunteerTask; }
    public void setVolunteerTask(String volunteerTask) { this.volunteerTask = volunteerTask; }

    @Override
    public String toString() {
        return String.format("Фестиваль{id=%d, company_id=%d, performance_id=%d, '%s' '%s' }", eventId, companyId, performanceId, address, date);
    }
}
