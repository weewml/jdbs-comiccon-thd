package com.comiccon.model;

/**
 * Критерии оценивания
 */
public class Criterions {
    private int criterion_id;
    private int difficult_mark;
    private int artistic_mark;

    public Criterions() {}

    public Criterions(int criterion_id, int difficult_mark, int artistic_mark) {
        this.criterion_id = criterion_id;
        this.difficult_mark = difficult_mark;
        this.artistic_mark = artistic_mark;
    }

    public Criterions(int difficult_mark, int artistic_mark) {
        this(0,difficult_mark, artistic_mark);
    }

    public int getCriterion_id() { return criterion_id; }
    public void setCriterion_id(int criterion_id) { this.criterion_id = criterion_id; }
    public int getDifficult_mark() { return difficult_mark; }
    public void setDifficult_mark(int difficult_mark) { this.difficult_mark = difficult_mark; }
    public int getArtistic_mark() { return artistic_mark; }
    public void setArtistic_mark(int artistic_mark) { this.artistic_mark = artistic_mark; }

    @Override
    public String toString() {
        return String.format("Критерии оценивания{id=%d, сложность:'%s', артистизм:'%s'}", criterion_id, difficult_mark, artistic_mark);
    }
}
