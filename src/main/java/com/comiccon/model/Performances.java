package com.comiccon.model;

/**
 * Выступление.
 */
public class Performances {
    private int performanceId;
    private int memberId;
    private String nomination;
    private String topic;

    // доп поля для JOIN запросов
    private String memberLastName;
    private String memberFirstName;
    private String memberHero;

    public Performances() { }

    public Performances(int performanceId, int memberId, String nomination, String topic) {
        this.performanceId = performanceId;
        this.memberId = memberId;
        this.nomination = nomination;
        this.topic = topic;
    }

    public Performances(int memberId, String nomination, String topic) {
        this(0, memberId, nomination, topic);
    }

    public int getPerformanceId() { return performanceId; }
    public void setPerformanceId(int performanceId) { this.performanceId = performanceId; }
    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }
    public String getNomination() { return nomination; }
    public void setNomination(String nomination) { this.nomination = nomination; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    // геттеры/сеттеры для JOIN
    public String getMemberLastName() { return memberLastName; }
    public void setMemberLastName(String memberLastName) { this.memberLastName = memberLastName; }
    public String getMemberFirstName() { return memberFirstName; }
    public void setMemberFirstName(String memberFirstName) { this.memberFirstName = memberFirstName; }
    public String getMemberHero() { return memberHero; }
    public void setMemberHero(String memberHero) { this.memberHero = memberHero; }

    @Override
    public String toString() {
        return String.format("Выступление{id=%d, участник=%d, номинация='%s', тема='%s' }", performanceId, memberId, nomination, topic);
    }
}
