package com.comiccon.dao;

import com.comiccon.db.ConnectionManager;
import com.comiccon.model.Performances;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO для таблицы performances
 */
public class PerformancesDao {

    // Поиск всех выступлений
    public List<Performances> findAll() throws SQLException {
        String sql = "SELECT performance_id, member_id, nomination, topic FROM comiccon.performances ORDER BY performance_id";
        List<Performances> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) result.add(mapRow(rs));
        }
        return result;
    }

    // Поиск выступления по айди
    public Optional<Performances> findById(int id) throws SQLException {
        String sql = "SELECT performance_id, member_id, nomination, topic FROM comiccon.performances WHERE performance_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
    }

    // Добавление нового выступления
    public int insert(Performances performance) throws SQLException {
        String sql = "INSERT INTO comiccon.performances (member_id, nomination, topic) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, performance.getMemberId());
            ps.setString(2, performance.getNomination());
            ps.setString(3, performance.getTopic());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    performance.setPerformanceId(id);
                    return id;
                }
            }
            throw new SQLException("Не удалось получить ключ");
        }
    }

    // Обновление участника, номинации, темы выступления
    public boolean update(Performances performance) throws SQLException {
        String sql = "UPDATE comiccon.performances SET member_id = ?, nomination = ?, topic = ? " +
                "WHERE performance_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, performance.getMemberId());
            ps.setString(2, performance.getNomination());
            ps.setString(3, performance.getTopic());
            ps.setInt(4, performance.getPerformanceId());
            return ps.executeUpdate() > 0;
        }
    }

    // Удаление выступления по айди
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM comiccon.performances WHERE performance_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Performances mapRow(ResultSet rs) throws SQLException {
        return new Performances(
                rs.getInt("performance_id"),
                rs.getInt("member_id"),
                rs.getString("nomination"),
                rs.getString("topic")
        );
    }
}
