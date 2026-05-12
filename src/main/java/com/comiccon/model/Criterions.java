package com.comiccon.model;

/**
 * Критерии оценивания
 */
public class Criterions {
    private int criterionId;
    private int difficultMark;
    private int artisticMark;

    public Criterions() {}

    public Criterions(int criterionId, int difficultMark, int artisticMark) {
        this.criterionId = criterionId;
        this.difficultMark = difficultMark;
        this.artisticMark = artisticMark;
    }

    public Criterions(int difficultMark, int artisticMark) {
        this(0, difficultMark, artisticMark);
    }

    public int getCriterionId() { return criterionId; }
    public void setCriterionId(int criterionId) { this.criterionId = criterionId; }
    public int getDifficultMark() { return difficultMark; }
    public void setDifficultMark(int difficultMark) { this.difficultMark = difficultMark; }
    public int getArtisticMark() { return artisticMark; }
    public void setArtisticMark(int artisticMark) { this.artisticMark = artisticMark; }

    @Override
    public String toString() {
        return String.format("Критерии оценивания{id=%d, сложность:'%s', артистизм:'%s'}", criterionId, difficultMark, artisticMark);
    }
}
