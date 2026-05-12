package com.comiccon.model;

/**
 * Оценка
 */
public class Marks {
    private int markId;
    private int juryId;
    private int criterionId;
    private int performanceId;

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

    @Override
    public String toString() {
        return String.format("Оценка{id=%d, жюри=%d, критерии=%d, выступление=%d}", markId, juryId, criterionId, performanceId);
    }
}
