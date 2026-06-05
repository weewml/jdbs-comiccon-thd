package com.comiccon.model;

/**
 * Компании-стенды
 */
public class Shops {
    private int companyId;
    private int artistId;
    private String companyName;
    private int area;
    private int numberTable;

    // доп поля для JOIN запросов
    private String artistLastName;
    private String artistFirstName;

    public Shops() {}

    public Shops(int companyId, int artistId, String companyName, int area, int numberTable) {
        this.companyId = companyId;
        this.artistId = artistId;
        this.companyName = companyName;
        this.area = area;
        this.numberTable = numberTable;
    }

    public Shops(int artistId, String companyName, int area, int numberTable) {
        this(0, artistId, companyName, area, numberTable);
    }

    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }
    public int getArtistId() { return artistId; }
    public void setArtistId(int artistId) { this.artistId = artistId; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public int getArea() { return area; }
    public void setArea(int area) { this.area = area; }
    public int getNumberTable() { return numberTable; }
    public void setNumberTable(int numberTable) { this.numberTable = numberTable; }

    // геттеры/сеттеры для JOIN
    public String getArtistLastName() { return artistLastName; }
    public void setArtistLastName(String artistLastName) { this.artistLastName = artistLastName; }
    public String getArtistFirstName() { return artistFirstName; }
    public void setArtistFirstName(String artistFirstName) { this.artistFirstName = artistFirstName; }

    @Override
    public String toString() {
        return String.format("Компании-стенды{id=%d, художник=%d, название='%s', площадь=%d, стол=%d }", companyId, artistId, companyName, area, numberTable);
    }
}
