package com.comiccon;

import com.comiccon.db.SchemaInitializer;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== JDBC Comiccon (Java 21 · PostgreSQL 17 · HikariCP) ===\n");

        try {
            SchemaInitializer.initialize();
            System.out.println("БД готова.\n");
        } catch (SQLException e) {
            System.err.println("Ошибка инициализации: " + e.getMessage());
            return;
        }

    }
}