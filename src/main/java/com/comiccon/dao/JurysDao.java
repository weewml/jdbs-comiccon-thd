package com.comiccon.dao;

import com.comiccon.db.ConnectionManager;
import com.comiccon.model.Jurys;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * DAO для таблицы jurys
 */
public class JurysDao {

    // Поиск всех жюри
    public List<Jurys> findAll() throws SQLException {
        String sql = "SELECT jury_id, last_name, first_name, patronymic FROM comiccon.jurys ORDER BY jury_id";
        List<Jurys> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) result.add(mapRow(rs));
        }
        return result;
    }

    // Поиск жюри по айди
    public Optional<Jurys> findById(int id) throws SQLException {
        String sql = "SELECT jury_id, last_name, first_name, patronymic FROM comiccon.jurys WHERE jury_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
    }

    // Добавление нового жюри
    public int insert(Jurys jury) throws SQLException {
        String sql = "INSERT INTO comiccon.jurys (last_name, first_name, patronymic) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, jury.getLastName());
            ps.setString(2, jury.getFirstName());
            ps.setString(3, jury.getPatronymic());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    jury.setJuryId(id);
                    return id;
                }
            }
            throw new SQLException("Не удалось получить ключ");
        }
    }

    // Обновление ФИО жюри
    public boolean update(Jurys jury) throws SQLException {
        String sql = "UPDATE comiccon.jurys SET last_name = ?, first_name = ?, patronymic = ? WHERE jury_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, jury.getLastName());
            ps.setString(2, jury.getFirstName());
            ps.setString(3, jury.getPatronymic());
            ps.setInt(4, jury.getJuryId());
            return ps.executeUpdate() > 0;
        }
    }

    // Удаление жюри по айди
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM comiccon.jurys WHERE jury_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Jurys mapRow(ResultSet rs) throws SQLException {
        return new Jurys(
                rs.getInt("jury_id"),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getString("patronymic")
        );
    }
}
