package com.comiccon.model;

/**
 * Критерии оценивания
 */
public class Criterions {
    private int criterionId;
    private double difficultMark;
    private double artisticMark;

    public Criterions() {}

    public Criterions(int criterionId, double difficultMark, double artisticMark) {
        this.criterionId = criterionId;
        this.difficultMark = difficultMark;
        this.artisticMark = artisticMark;
    }

    public Criterions(double difficultMark, double artisticMark) {
        this(0, difficultMark, artisticMark);
    }

    public int getCriterionId() { return criterionId; }
    public void setCriterionId(int criterionId) { this.criterionId = criterionId; }
    public double getDifficultMark() { return difficultMark; }
    public void setDifficultMark(double difficultMark) { this.difficultMark = difficultMark; }
    public double getArtisticMark() { return artisticMark; }
    public void setArtisticMark(double artisticMark) { this.artisticMark = artisticMark; }

    @Override
    public String toString() {
        return String.format("Критерии оценивания{id=%d, сложность:'%s', артистизм:'%s'}", criterionId, difficultMark, artisticMark);
    }
}
