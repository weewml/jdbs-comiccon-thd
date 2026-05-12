package com.comiccon.model;

/**
 * Выступление.
 */
public class Performances {
    private int performanceId;
    private int memberId;
    private String nomination;
    private String topic;

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

    @Override
    public String toString() {
        return String.format("Выступление{id=%d, участник=%d, номинация='%s', тема='%s' }", performanceId, memberId, nomination, topic);
    }
}
