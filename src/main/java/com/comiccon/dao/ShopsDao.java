package com.comiccon.dao;

import com.comiccon.db.ConnectionManager;
import com.comiccon.model.Shops;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO для таблицы shops
 */
public class ShopsDao {

    // Поиск всех стендов-компаний
    public List<Shops> findAll() throws SQLException {
        String sql = "SELECT company_id, artist_id, company_name, area, number_table FROM comiccon.shops ORDER BY company_id";
        List<Shops> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) result.add(mapRow(rs));
        }
        return result;
    }

    // Поиск стенда-компании по айди
    public Optional<Shops> findById(int id) throws SQLException {
        String sql = "SELECT company_id, artist_id, company_name, area, number_table FROM comiccon.shops WHERE company_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
    }

    // Добавление нового стенда-компании
    public int insert(Shops shop) throws SQLException {
        String sql = "INSERT INTO comiccon.shops (artist_id, company_name, area, number_table) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, shop.getArtistId());
            ps.setString(2, shop.getCompanyName());
            ps.setInt(3, shop.getArea());
            ps.setInt(4, shop.getNumberTable());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    shop.setCompanyId(id);
                    return id;
                }
            }
            throw new SQLException("Не удалось получить ключ");
        }
    }

    // Обновление художника, названия компании, площади, номера стола стенда-компании
    public boolean update(Shops shop) throws SQLException {
        String sql = "UPDATE comiccon.shops SET artist_id = ?, company_name = ?, area = ?, number_table = ? " +
                "WHERE company_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, shop.getArtistId());
            ps.setString(2, shop.getCompanyName());
            ps.setInt(3, shop.getArea());
            ps.setInt(4, shop.getNumberTable());
            ps.setInt(5, shop.getCompanyId());
            return ps.executeUpdate() > 0;
        }
    }

    // Удаление стенда-компании по айди
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM comiccon.shops WHERE company_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Shops mapRow(ResultSet rs) throws SQLException {
        return new Shops(
                rs.getInt("company_id"),
                rs.getInt("artist_id"),
                rs.getString("company_name"),
                rs.getInt("area"),
                rs.getInt("number_table")
        );
    }
}
