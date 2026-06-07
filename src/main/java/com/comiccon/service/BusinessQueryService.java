package com.comiccon.service;

import com.comiccon.dao.*;
import com.comiccon.db.ConnectionManager;
import com.comiccon.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class BusinessQueryService {

    private final ShopsDao shopsDao = new ShopsDao();
    private final PerformancesDao performancesDao = new PerformancesDao();
    private final MarksDao marksDao = new MarksDao();
    private final FestDao festDao = new FestDao();

    public void top5MembersByMarks() throws SQLException {
        System.out.println("\n=== Топ-5 участников по сумме баллов (сложность + артистизм) ===");
        String sql = """
                SELECT
                    mb.last_name AS last_name,
                    mb.first_name AS first_name,
                    mb.hero AS m_hero,
                    SUM(c.difficult_mark + c.artistic_mark) AS sum_mark
                FROM comiccon.marks m
                JOIN comiccon.criterions c ON m.criterion_id = c.criterion_id
                JOIN comiccon.performances p ON m.performance_id = p.performance_id
                JOIN comiccon.members mb ON p.member_id = mb.member_id
                GROUP BY mb.member_id, mb.last_name, mb.first_name, mb.hero
                ORDER BY sum_mark DESC
                LIMIT 5
                """;
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.printf("%-5s %-15s %-10s %-20s %-12s%n",
                    "Место", "Фамилия", "Имя", "Герой", "Сумма баллов");
            int rank = 1;
            while (rs.next()) {
                System.out.printf("%-5d %-15s %-10s %-20s %-12.2f%n",
                        rank++,
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("m_hero"),
                        rs.getDouble("sum_mark"));
            }
        }
        System.out.println();
    }

    public void festByDate(LocalDate date) throws SQLException {
        System.out.printf("\n=== Мероприятия на %s ===%n", date);
        String sql = """
                SELECT
                    s.company_name AS company,
                    p.topic AS performance_topic,
                    v.last_name AS volunteer_last,
                    v.first_name AS volunteer_first,
                    v.task AS volunteer_task,
                    f.address AS f_address,
                    f.date AS f_date
                FROM comiccon.fest f
                JOIN comiccon.shops s ON f.company_id = s.company_id
                JOIN comiccon.performances p ON f.performance_id = p.performance_id
                LEFT JOIN comiccon.volunteers v ON f.volunteer_id = v.volunteer_id
                WHERE DATE(f.date) = ?
                ORDER BY f.date
                """;
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, date);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.printf("%-15s %-25s %-15s %-10s %-25s %-25s %-20s%n",
                        "Компания", "Тема выступления", "Волонтер (фам)", "Имя", "Задание", "Адрес", "Время");
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.printf("%-15s %-25s %-15s %-10s %-25s %-25s %-20s%n",
                            rs.getString("company"),
                            truncate(rs.getString("performance_topic"), 25),
                            rs.getString("volunteer_last") == null ? "—" : rs.getString("volunteer_last"),
                            rs.getString("volunteer_first") == null ? "—" : rs.getString("volunteer_first"),
                            rs.getString("volunteer_task") == null ? "—" : truncate(rs.getString("volunteer_task"), 25),
                            truncate(rs.getString("f_address"), 25),
                            rs.getTimestamp("f_date").toLocalDateTime().toLocalTime());
                }
                if (!found) {
                    System.out.println("На указанную дату мероприятий не найдено");
                }
            }
        }
        System.out.println();
    }

    public void unUsedVolunteers() throws SQLException {
        System.out.println("\n=== Волонтеры, не закрепленные ни за одним мероприятием ===");
        String sql = """
                SELECT
                    v.volunteer_id AS id,
                    v.last_name AS last,
                    v.first_name AS first,
                    v.patronymic AS patr,
                    v.task AS ts
                FROM comiccon.volunteers v
                LEFT JOIN comiccon.fest f ON v.volunteer_id = f.volunteer_id
                WHERE f.volunteer_id IS NULL
                ORDER BY v.volunteer_id
                """;
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.printf("%-5s %-15s %-10s %-15s %-25s%n",
                    "ID", "Фамилия", "Имя", "Отчество", "Задание");
            while (rs.next()) {
                System.out.printf("%-5d %-15s %-10s %-15s %-25s%n",
                        rs.getInt("id"),
                        rs.getString("last"),
                        rs.getString("first"),
                        rs.getString("patr") == null ? "" : rs.getString("patr"),
                        rs.getString("ts"));
            }
        }
        System.out.println();
    }

    public void shopsAreaUpAvg() throws SQLException {
        System.out.println("\n=== Компании-стенды с площадью выше средней ===");
        String sql = """
                SELECT
                    company_id AS id,
                    company_name AS n,
                    area AS ar
                FROM comiccon.shops
                WHERE area > (SELECT AVG(area) FROM comiccon.shops)
                ORDER BY area DESC
                """;
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            double avg = getAvgArea();
            System.out.printf("Средняя площадь: %.2f\n", avg);
            System.out.printf("%-5s %-15s %-8s%n", "ID", "Компания", "Площадь");
            while (rs.next()) {
                System.out.printf("%-5d %-15s %-8d%n",
                        rs.getInt("id"),
                        rs.getString("n"),
                        rs.getInt("ar"));
            }
        }
        System.out.println();
    }

    public void companyWithEvents() throws SQLException {
        System.out.println("\n=== Статистика, насколько задействована компания ===");
        String sql = """
                SELECT
                    s.company_name AS company,
                    COUNT(f.event_id) AS ev,
                    COUNT(p.performance_id) AS perf,
                    COUNT(v.volunteer_id) AS vol
                FROM comiccon.shops s
                LEFT JOIN comiccon.fest f ON s.company_id = f.company_id
                LEFT JOIN comiccon.performances p ON f.performance_id = p.performance_id
                LEFT JOIN comiccon.volunteers v ON f.volunteer_id = v.volunteer_id
                GROUP BY s.company_id, s.company_name
                ORDER BY ev DESC
                """;
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.printf("%-15s %-12s %-12s %-12s%n",
                    "Компания", "Мероприятий", "Выступлений", "Волонтеров");
            while (rs.next()) {
                System.out.printf("%-15s %-12d %-12d %-12d%n",
                        rs.getString("company"),
                        rs.getInt("ev"),
                        rs.getInt("perf"),
                        rs.getInt("vol"));
            }
        }
        System.out.println();
    }

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

    private double getAvgArea() throws SQLException {
        String sql = "SELECT AVG(area) FROM comiccon.shops";
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(1);
            return 0;
        }
    }

    private static String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() > max ? s.substring(0, max - 1) + "…" : s;
    }
}
