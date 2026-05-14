package com.comiccon.service;

import com.comiccon.dao.*;
import com.comiccon.model.*;

import java.sql.SQLException;

public class CrudService {

    private final ArtistsDao artistsDao = new ArtistsDao();
    private final CriterionsDao criterionsDao = new CriterionsDao();
    private final FestDao festDao = new FestDao();
    private final JurysDao jurysDao = new JurysDao();
    private final MarksDao marksDao = new MarksDao();
    private final MembersDao membersDao = new MembersDao();
    private final PerformancesDao performancesDao = new PerformancesDao();
    private final ShopsDao shopsDao = new ShopsDao();
    private final VolunteersDao volunteersDao = new VolunteersDao();

    // CREATE

    // Чтение художников
    public void readArtists() throws SQLException {
        System.out.println("=== READ artists ===");

        System.out.printf("%-5s %-12s %-12s %-12s%n", "ID", "Фамилия", "Имя", "Отчество");
        for (Artists a : artistsDao.findAll()) {
            System.out.printf("%-5s %-12s %-12s %-12s%n",
                    a.getArtistId(), a.getLastName(), a.getFirstName(), a.getPatronymic());
        }

        System.out.println();
    }

    // Чтение критериев
    public void readCriterions() throws SQLException {
        System.out.println("=== READ criterions ===");

        System.out.printf("%-5s %-10s %-10s%n", "ID", "Сложность", "Артистичность");
        for (Criterions c : criterionsDao.findAll()) {
            System.out.printf("%-5s %-10s %-10s%n",
                    c.getCriterionId(), c.getDifficultMark(), c.getArtisticMark());
        }

        System.out.println();
    }

    // Чтение мероприятий фестиваля
    public void readFest() throws SQLException {
        System.out.println("=== READ fest ===");

        System.out.printf("%-5s %-8s %-8s %-8s %-20s %-15s%n",
                "ID", "ID-комп", "ID-выст", "ID-вол", "Адрес", "Дата");
        for (Fest f : festDao.findAll()) {
            System.out.printf("%-5s %-8s %-8s %-8s %-20s %-15s%n",
                    f.getEventId(), f.getCompanyId(), f.getPerformanceId(),
                    f.getVolunteerId(), f.getAddress(), f.getDate());
        }

        System.out.println();
    }
}
