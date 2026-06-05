package com.comiccon.dao;

import com.comiccon.db.ConnectionManager;
import com.comiccon.model.Criterions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO для таблицы criterions
 */
public class CriterionsDao {

    // Поиск всех критериев оценивания
    public List<Criterions> findAll() throws SQLException {
        String sql = "SELECT criterion_id, difficult_mark, artistic_mark FROM comiccon.criterions ORDER BY criterion_id";
        List<Criterions> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) result.add(mapRow(rs));
        }
        return result;
    }

    // Поиск критериев оценивания по айди
    public Optional<Criterions> findById(int id) throws SQLException {
        String sql = "SELECT criterion_id, difficult_mark, artistic_mark FROM comiccon.criterions WHERE criterion_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
    }

    // Добавление нового критерия оценивания
    public int insert(Criterions criterion) throws SQLException {
        String sql = "INSERT INTO comiccon.criterions (difficult_mark, artistic_mark) VALUES (?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, criterion.getDifficultMark());
            ps.setDouble(2, criterion.getArtisticMark());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    criterion.setCriterionId(id);
                    return id;
                }
            }
            throw new SQLException("Не удалось получить ключ");
        }
    }

    // Обновление сложности и артистичности критериев оценивания
    public boolean update(Criterions criterion) throws SQLException {
        String sql = "UPDATE comiccon.criterions SET difficult_mark = ?, artistic_mark = ? WHERE criterion_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, criterion.getDifficultMark());
            ps.setDouble(2, criterion.getArtisticMark());
            ps.setInt(1, criterion.getCriterionId());
            return ps.executeUpdate() > 0;
        }
    }

    // Удаление критериев оценивания по айди
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM comiccon.criterions WHERE criterion_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Criterions mapRow(ResultSet rs) throws SQLException {
        return new Criterions(
                rs.getInt("criterion_id"),
                rs.getDouble("difficult_mark"),
                rs.getDouble("artistic_mark")
        );
    }
}
