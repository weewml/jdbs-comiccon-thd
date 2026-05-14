package com.comiccon.dao;


import com.comiccon.db.ConnectionManager;
import com.comiccon.model.Members;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO для таблицы members.
 */
public class MembersDao {

    // Поиск всех учатсников
    public List<Members> findAll() throws SQLException {
        String sql = "SELECT member_id, last_name, first_name, patronymic, hero, original_source " +
                "FROM comiccon.members ORDER BY member_id";
        List<Members> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) result.add(mapRow(rs));
        }
        return result;
    }

    // Поиск участникво по айди
    public Optional<Members> findById(int id) throws SQLException {
        String sql = "SELECT member_id, last_name, first_name, patronymic, hero, original_source " +
                "FROM comiccon.members WHERE member_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
    }

    // Добавление нового участника
    public int insert(Members member) throws SQLException {
        String sql = "INSERT INTO comiccon.members (last_name, first_name, patronymic, hero, original_source) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, member.getLastName());
            ps.setString(2, member.getFirstName());
            ps.setString(3, member.getPatronymic());
            ps.setString(4, member.getHero());
            ps.setString(5, member.getOriginalSource());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    member.setMemberId(id);
                    return id;
                }
            }
            throw new SQLException("Не удалось получить ключ");
        }
    }

    // Обновление ФИО, героя и источника участника
    public boolean update(Members member) throws SQLException {
        String sql = "UPDATE comiccon.members SET last_name = ?, first_name = ?, patronymic = ?, hero = ?, original_source = ? " +
                "WHERE member_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getLastName());
            ps.setString(2, member.getFirstName());
            ps.setString(3, member.getPatronymic());
            ps.setString(4, member.getHero());
            ps.setString(5, member.getOriginalSource());
            ps.setInt(6, member.getMemberId());
            return ps.executeUpdate() > 0;
        }
    }

    // Удаление участника по айди
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM comiccon.members WHERE member_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Members mapRow(ResultSet rs) throws SQLException {
        return new Members(
                rs.getInt("member_id"),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getString("patronymic"),
                rs.getString("hero"),
                rs.getString("original_source")
        );
    }
}
