package com.comiccon.model;

/**
 * Оценка
 */
public class Marks {
    private int markId;
    private int juryId;
    private int criterionId;
    private int performanceId;

    // доп поля для JOIN запросов
    private String performanceTopic;
    private String memberLastName;
    private String memberHero;
    private double difficultMark;
    private double artisticMark;
    private String juryLastName;

    public Marks() { }

    public Marks(int markId, int juryId, int criterionId, int performanceId) {
        this.markId = markId;
        this.juryId = juryId;
        this.criterionId = criterionId;
        this.performanceId = performanceId;
    }

    public Marks(int juryId, int criterionId, int performanceId) {
        this(0, juryId, criterionId, performanceId);
    }

    public int getMarkId() { return markId; }
    public void setMarkId(int markId) { this.markId = markId; }
    public int getJuryId() { return juryId; }
    public void setJuryId(int juryId) { this.juryId = juryId; }
    public int getCriterionId() { return criterionId; }
    public void setCriterionId(int criterionId) { this.criterionId = criterionId; }
    public int getPerformanceId() { return performanceId; }
    public void setPerformanceId(int performanceId) { this.performanceId = performanceId; }

    // геттеры/сеттеры для JOIN
    public String getPerformanceTopic() { return performanceTopic; }
    public void setPerformanceTopic(String performanceTopic) { this.performanceTopic = performanceTopic; }
    public String getMemberLastName() { return memberLastName; }
    public void setMemberLastName(String memberLastName) { this.memberLastName = memberLastName; }
    public String getMemberHero() { return memberHero; }
    public void setMemberHero(String memberHero) { this.memberHero = memberHero; }
    public double getDifficultMark() { return difficultMark; }
    public void setDifficultMark(double difficultMark) { this.difficultMark = difficultMark; }
    public double getArtisticMark() { return artisticMark; }
    public void setArtisticMark(double artisticMark) { this.artisticMark = artisticMark; }
    public String getJuryLastName() { return juryLastName; }
    public void setJuryLastName(String juryLastName) { this.juryLastName = juryLastName; }

    @Override
    public String toString() {
        return String.format("Оценка{id=%d, жюри=%d, критерии=%d, выступление=%d}", markId, juryId, criterionId, performanceId);
    }
}
