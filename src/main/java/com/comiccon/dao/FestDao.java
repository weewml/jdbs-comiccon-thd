package com.comiccon.dao;

import com.comiccon.db.ConnectionManager;
import com.comiccon.model.Fest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO для таблицы fest
 */
public class FestDao {

    // Поиск всех мероприятий фестиваля
    public List<Fest> findAll() throws SQLException {
        String sql = "SELECT event_id, company_id, performance_id, volunteer_id, address, date " +
                "FROM comiccon.fest ORDER BY event_id";
        List<Fest> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) result.add(mapRow(rs));
        }
        return result;
    }

    // Поиск мероприятия фестиваля по айди
    public Optional<Fest> findById(int id) throws SQLException {
        String sql = "SELECT event_id, company_id, performance_id, volunteer_id, address, date " +
                "FROM comiccon.fest WHERE event_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
    }

    public List<Fest> findFestWithDetails() throws SQLException {
        String sql = """
        SELECT f.event_id, f.company_id, f.performance_id, f.volunteer_id, f.address, f.date,
               s.company_name,
               p.topic AS performance_topic,
               v.last_name AS volunteer_last_name, v.task AS volunteer_task
        FROM comiccon.fest f
        JOIN comiccon.shops s ON s.company_id = f.company_id
        JOIN comiccon.performances p ON p.performance_id = f.performance_id
        LEFT JOIN comiccon.volunteers v ON v.volunteer_id = f.volunteer_id
        ORDER BY f.event_id
        """;
        List<Fest> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Fest fest = mapRow(rs);
                fest.setCompanyName(rs.getString("company_name"));
                fest.setPerformanceTopic(rs.getString("performance_topic"));
                fest.setVolunteerLastName(rs.getString("volunteer_last_name"));
                fest.setVolunteerTask(rs.getString("volunteer_task"));
                result.add(fest);
            }
        }
        return result;
    }

    // Добавление нового мероприятия фестиваля
    public int insert(Fest fest) throws SQLException {
        String sql = "INSERT INTO comiccon.fest (company_id, performance_id, volunteer_id, address, date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, fest.getCompanyId());
            ps.setInt(2, fest.getPerformanceId());
            ps.setInt(3, fest.getVolunteerId());
            ps.setString(4, fest.getAddress());
            ps.setDate(5, Date.valueOf(fest.getDate()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    fest.setEventId(id);
                    return id;
                }
            }
            throw new SQLException("Не удалось получить ключ");
        }
    }

    // Обновление компании, выступления, волонтера, адреса и даты мероприятия фестиваля
    public boolean update(Fest fest) throws SQLException {
        String sql = "UPDATE comiccon.fest SET company_id = ?, performance_id = ?, volunteer_id = ?, address = ?, date = ? " +
                "WHERE event_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, fest.getCompanyId());
            ps.setInt(2, fest.getPerformanceId());
            ps.setInt(3, fest.getVolunteerId());
            ps.setString(4, fest.getAddress());
            ps.setDate(5, Date.valueOf(fest.getDate()));
            ps.setInt(6, fest.getEventId());
            return ps.executeUpdate() > 0;
        }
    }

    // Удаление выступления по айди
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM comiccon.fest WHERE event_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Fest mapRow(ResultSet rs) throws SQLException {
        return new Fest(
                rs.getInt("event_id"),
                rs.getInt("company_id"),
                rs.getInt("performance_id"),
                rs.getInt("volunteer_id"),
                rs.getString("address"),
                rs.getDate("date").toLocalDate()
        );
    }
}
