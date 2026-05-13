package com.comiccon.dao;

import com.comiccon.db.ConnectionManager;
import com.comiccon.model.Volunteers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO для таблицы volunteers
 */
public class VolunteersDao {

    // Поиск всех волонтеров
    public List<Volunteers> findAll() throws SQLException {
        String sql = "SELECT volunteer_id, last_name, first_name, patronymic, task FROM volunteers ORDER BY volunteer_id";
        List<Volunteers> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) result.add(mapRow(rs));
        }
        return result;
    }

    // Поиск волонтера по айди
    public Optional<Volunteers> findById(int id) throws SQLException {
        String sql = "SELECT volunteer_id, last_name, first_name, patronymic, task FROM volunteers WHERE volunteer_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
    }

    // Добавление нового волонтера
    public int insert(Volunteers volunteer) throws SQLException {
        String sql = "INSERT INTO volunteers (last_name, first_name, patronymic, task) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, volunteer.getLastName());
            ps.setString(2, volunteer.getFirstName());
            ps.setString(3, volunteer.getPatronymic());
            ps.setString(4, volunteer.getTask());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    volunteer.setVolunteerId(id);
                    return id;
                }
            }
            throw new SQLException("Не удалось получить ключ");
        }
    }

    // Обновление ФИО и задания волонтера
    public boolean update(Volunteers volunteer) throws SQLException {
        String sql = "UPDATE volunteers SET last_name = ?, first_name = ?, patronymic = ?, task = ? WHERE volunteer_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, volunteer.getLastName());
            ps.setString(2, volunteer.getFirstName());
            ps.setString(3, volunteer.getPatronymic());
            ps.setString(4, volunteer.getTask());
            ps.setInt(5, volunteer.getVolunteerId());
            return ps.executeUpdate() > 0;
        }
    }

    // Удаление волонтера по айди
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM volunteers WHERE volunteer_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Volunteers mapRow(ResultSet rs) throws SQLException {
        return new Volunteers(
                rs.getInt("volunteer_id"),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getString("patronymic"),
                rs.getString("task")
        );
    }

}
