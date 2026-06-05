package com.comiccon.service;

import com.comiccon.dao.*;
import com.comiccon.model.*;

import java.sql.SQLException;
import java.util.List;

public class BusinessQueryService {

    private final ShopsDao shopsDao = new ShopsDao();
    private final PerformancesDao performancesDao = new PerformancesDao();
    private final MarksDao marksDao = new MarksDao();
    private final FestDao festDao = new FestDao();

    public void readShopsWithArtists() throws SQLException {
        System.out.println("\n=== Магазины-стенды + JOIN художники ===");
        List<Shops> shops = shopsDao.findShopsWithArtists();
        System.out.printf("%-6s %-6s %-15s %-10s %-20s %-5s %-5s%n",
                "ID ком", "ID худ", "Фам. худ.", "Имя худ.", "Компания", "Площ", "Стол");
        for (Shops s : shops) {
            System.out.printf("%-6d %-6d %-15s %-10s %-20s %-5d %-5d%n",
                    s.getCompanyId(), s.getArtistId(), s.getArtistLastName(),
                    s.getArtistFirstName(), s.getCompanyName(), s.getArea(), s.getNumberTable());
        }
    }

    public void readPerformancesWithMembers() throws SQLException {
        System.out.println("\n=== Выступления + JOIN участники ===");
        List<Performances> perfs = performancesDao.findPerformancesWithMembers();
        System.out.printf("%-7s %-5s %-15s %-10s %-20s %-12s %-25s%n",
                "ID выст", "ID уч", "Фам.", "Имя", "Герой", "Номинация", "Тема");
        for (Performances p : perfs) {
            System.out.printf("%-7d %-5d %-15s %-10s %-20s %-12s %-25s%n",
                    p.getPerformanceId(), p.getMemberId(), p.getMemberLastName(),
                    p.getMemberFirstName(), p.getMemberHero(), p.getNomination(), p.getTopic());
        }
    }

    public void readMarksWithDetails() throws SQLException {
        System.out.println("\n=== Оценки + JOIN участники, критерии оценивания, жюри ===");
        List<Marks> marks = marksDao.findMarksWithDetails();
        System.out.printf("%-17s %-18s %-9s %-9s %-12s %-22s%n",
                "Фам. участника", "Герой", "Сложн.", "Артист.", "Фам. жюри", "Тема");
        for (Marks m : marks) {
            System.out.printf("%-17s %-18s %-9.2f %-9.2f %-12s %-22s%n",
                    m.getMemberLastName(), m.getMemberHero(),
                    m.getDifficultMark(), m.getArtisticMark(), m.getJuryLastName(),
                    m.getPerformanceTopic());
        }
    }

    public void readFestWithDetails() throws SQLException {
        System.out.println("\n=== Фестиваль + JOIN компания, выступления, волонтер ===");
        List<Fest> fests = festDao.findFestWithDetails();
        System.out.printf("%-20s %-22s %-15s %-22s %-25s %-20s%n",
                "Компания", "Тема", "Волонтер", "Задание", "Адрес", "Дата");
        for (Fest f : fests) {
            System.out.printf("%-20s %-22s %-15s %-22s %-25s %-20s%n",
                    f.getCompanyName(),
                    truncate(f.getPerformanceTopic(), 22),
                    f.getVolunteerLastName() == null ? "-" : f.getVolunteerLastName(),
                    f.getVolunteerTask() == null ? "-" : truncate(f.getVolunteerTask(), 22),
                    f.getAddress(), f.getDate());
        }
    }

    private static String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() > max ? s.substring(0, max - 1) + "…" : s;
    }
}
