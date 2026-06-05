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

    // READ
    // Чтение художников
    public void readArtists() throws SQLException {
        System.out.println("=== READ artists ===");

        System.out.printf("%-5s %-12s %-10s %-12s%n", "ID", "Фамилия", "Имя", "Отчество");
        for (Artists a : artistsDao.findAll()) {
            System.out.printf("%-5s %-12s %-10s %-12s%n",
                    a.getArtistId(), a.getLastName(), a.getFirstName(), a.getPatronymic());
        }

        System.out.println();
    }

    // Поиск художника по id
    public void findArtistById(int id) throws SQLException {
        System.out.printf("\nПоиск художника по id=%d\n", id);
        artistsDao.findById(id).ifPresentOrElse(
                a -> System.out.println(a),
                () -> System.out.println("Не найден"));

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

    // Поиск критериев оценивания по id
    public void findCriterionById(int id) throws SQLException {
        System.out.printf("\nПоиск критериев оценивания по id=%d\n", id);
        criterionsDao.findById(id).ifPresentOrElse(
                с -> System.out.println(с),
                () -> System.out.println("Не найден"));

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

    // Поиск мероприятия по id
    public void findFestById(int id) throws SQLException {
        System.out.printf("\nПоиск мероприятия по id=%d\n", id);
        festDao.findById(id).ifPresentOrElse(
                f -> System.out.println(f),
                () -> System.out.println("Не найден"));

        System.out.println();
    }

    // Чтение жюри
    public void readJurys() throws SQLException {
        System.out.println("=== READ jurys ===");

        System.out.printf("%-5s %-12s %-10s %-12s%n", "ID", "Фамилия", "Имя", "Отчество");
        for (Jurys j : jurysDao.findAll()) {
            System.out.printf("%-5s %-12s %-10s %-12s%n",
                    j.getJuryId(), j.getLastName(), j.getFirstName(), j.getPatronymic());
        }

        System.out.println();
    }

    // Поиск жюри по id
    public void findJuryById(int id) throws SQLException {
        System.out.printf("\nПоиск жюри по id=%d\n", id);
        jurysDao.findById(id).ifPresentOrElse(
                j -> System.out.println(j),
                () -> System.out.println("Не найден"));

        System.out.println();
    }


    // Чтение оценок
    public void readMarks() throws SQLException {
        System.out.println("=== READ marks ===");

        System.out.printf("%-5s %-8s %-8s %-8s%n",
                "ID", "ID-жюри", "ID-крит", "ID-выст");
        for (Marks m : marksDao.findAll()) {
            System.out.printf("%-5s %-8s %-8s %-8s%n",
                    m.getMarkId(), m.getJuryId(), m.getCriterionId(), m.getPerformanceId());
        }

        System.out.println();
    }

    // Поиск оценки по id
    public void findMarkById(int id) throws SQLException {
        System.out.printf("\nПоиск оценки по id=%d\n", id);
        marksDao.findById(id).ifPresentOrElse(
                m -> System.out.println(m),
                () -> System.out.println("Не найден"));

        System.out.println();
    }

    // Чтение участников
    public void readMembers() throws SQLException {
        System.out.println("=== READ members ===");

        System.out.printf("%-5s %-12s %-10s %-12s %-18s %-12s%n",
                "ID", "Фамилия", "Имя", "Отчество", "Герой", "Первоисточник");
        for (Members m : membersDao.findAll()) {
            System.out.printf("%-5s %-12s %-10s %-12s %-18s %-12s%n",
                    m.getMemberId(), m.getLastName(), m.getFirstName(), m.getPatronymic(),
                    m.getHero(), m.getOriginalSource());
        }

        System.out.println();
    }

    // Поиск участника по id
    public void findMemberById(int id) throws SQLException {
        System.out.printf("\nПоиск участника по id=%d\n", id);
        membersDao.findById(id).ifPresentOrElse(
                m -> System.out.println(m),
                () -> System.out.println("Не найден"));

        System.out.println();
    }

    // Чтение выступлений
    public void readPerformances() throws SQLException {
        System.out.println("=== READ performances ===");

        System.out.printf("%-5s %-10s %-12s %-12s%n",
                "ID", "ID-участ", "Номинация", "Тематика");
        for (Performances p : performancesDao.findAll()) {
            System.out.printf("%-5s %-10s %-12s %-12s%n",
                    p.getPerformanceId(), p.getMemberId(), p.getNomination(), p.getTopic());
        }

        System.out.println();
    }

    // Поиск выступления по id
    public void findPerformanceById(int id) throws SQLException {
        System.out.printf("\nПоиск выступления по id=%d\n", id);
        performancesDao.findById(id).ifPresentOrElse(
                p -> System.out.println(p),
                () -> System.out.println("Не найден"));

        System.out.println();
    }

    // Чтение магазинов-стендов компаний
    public void readShops() throws SQLException {
        System.out.println("=== READ shops ===");

        System.out.printf("%-5s %-12s %-18s %-12s %-12s%n",
                "ID", "ID-худож", "Название компании", "Площадь", "Номер стола");
        for (Shops s : shopsDao.findAll()) {
            System.out.printf("%-5s %-12s %-18s %-12s %-12s%n",
                    s.getCompanyId(), s.getArtistId(), s.getCompanyName(), s.getArea(), s.getNumberTable());
        }

        System.out.println();
    }

    // Поиск магазина-стенда компании по id
    public void findShopById(int id) throws SQLException {
        System.out.printf("\nПоиск магазина-стенда компании по id=%d\n", id);
        shopsDao.findById(id).ifPresentOrElse(
                s -> System.out.println(s),
                () -> System.out.println("Не найден"));

        System.out.println();
    }

    // Чтение волонтеров
    public void readVolunteers() throws SQLException {
        System.out.println("=== READ volunteers ===");

        System.out.printf("%-5s %-12s %-10s %-12s %-12s%n", "ID", "Фамилия", "Имя", "Отчество", "Задание");
        for (Volunteers v : volunteersDao.findAll()) {
            System.out.printf("%-5s %-12s %-10s %-12s %-12s%n",
                    v.getVolunteerId(), v.getLastName(), v.getFirstName(), v.getPatronymic(), v.getTask());
        }

        System.out.println();
    }

    // Поиск волонтера по id
    public void findVolunteerById(int id) throws SQLException {
        System.out.printf("\nПоиск волонтера по id=%d\n", id);
        volunteersDao.findById(id).ifPresentOrElse(
                v -> System.out.println(v),
                () -> System.out.println("Не найден"));

        System.out.println();
    }

    // СREATE

}
