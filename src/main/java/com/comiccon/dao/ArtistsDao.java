package com.comiccon.dao;

import com.comiccon.db.ConnectionManager;
import com.comiccon.model.Artists;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO для таблицы artists
 */
public class ArtistsDao {

    // Поиск всех художников
    public List<Artists> findAll() throws SQLException {
        String sql = "SELECT artist_id, last_name, first_name, patronymic FROM comiccon.artists ORDER BY artist_id";
        List<Artists> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) result.add(mapRow(rs));
        }
        return result;
    }

    // Поиск художника по айди
    public Optional<Artists> findById(int id) throws SQLException {
        String sql = "SELECT artist_id, last_name, first_name, patronymic FROM comiccon.artists WHERE artist_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
    }

    // Добавление нового художника
    public int insert(Artists artist) throws SQLException {
        String sql = "INSERT INTO comiccon.artists (last_name, first_name, patronymic) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, artist.getLastName());
            ps.setString(2, artist.getFirstName());
            ps.setString(3, artist.getPatronymic());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    artist.setArtistId(id);
                    return id;
                }
            }
            throw new SQLException("Не удалось получить ключ");
        }
    }

    // Обновление ФИО художника
    public boolean update(Artists artist) throws SQLException {
        String sql = "UPDATE comiccon.artists SET last_name = ?, first_name = ?, patronymic = ? WHERE artist_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artist.getLastName());
            ps.setString(2, artist.getFirstName());
            ps.setString(3, artist.getPatronymic());
            ps.setInt(4, artist.getArtistId());
            return ps.executeUpdate() > 0;
        }
    }

    // Удаление художника по айди
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM comiccon.artists WHERE artist_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Artists mapRow(ResultSet rs) throws SQLException {
        return new Artists(
                rs.getInt("artist_id"),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getString("patronymic")
        );
    }
}
