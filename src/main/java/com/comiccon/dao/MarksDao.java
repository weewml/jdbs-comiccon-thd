package com.comiccon.dao;

import com.comiccon.db.ConnectionManager;
import com.comiccon.model.Marks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO для таблицы marks
 */
public class MarksDao {

    // Поиск всех оценок
    public List<Marks> findAll() throws SQLException {
        String sql = "SELECT mark_id, jury_id, criterion_id, performance_id FROM marks ORDER BY mark_id";
        List<Marks> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) result.add(mapRow(rs));
        }
        return result;
    }

    // Поиск оценки по айди
    public Optional<Marks> findById(int id) throws SQLException {
        String sql = "SELECT mark_id, jury_id, criterion_id, performance_id FROM marks WHERE mark_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
    }

    // Добавление новой оценки
    public int insert(Marks mark) throws SQLException {
        String sql = "INSERT INTO marks (jury_id, criterion_id, performance_id) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, mark.getJuryId());
            ps.setInt(2, mark.getCriterionId());
            ps.setInt(3, mark.getPerformanceId());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    mark.setMarkId(id);
                    return id;
                }
            }
            throw new SQLException("Не удалось получить ключ");
        }
    }

    // Обновление жюри, критериев оценивания, выступления оценки
    public boolean update(Marks mark) throws SQLException {
        String sql = "UPDATE marks SET jury_id = ?, criterion_id = ?, performance_id = ? " +
                "WHERE mark_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mark.getJuryId());
            ps.setInt(2, mark.getCriterionId());
            ps.setInt(3, mark.getPerformanceId());
            ps.setInt(4, mark.getMarkId());
            return ps.executeUpdate() > 0;
        }
    }

    // Удаление оценки по айди
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM marks WHERE mark_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Marks mapRow(ResultSet rs) throws SQLException {
        return new Marks(
                rs.getInt("mark_id"),
                rs.getInt("jury_id"),
                rs.getInt("criterion_id"),
                rs.getInt("performance_id")
        );
    }
}
