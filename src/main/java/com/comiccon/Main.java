package com.comiccon;

import com.comiccon.db.SchemaInitializer;
import com.comiccon.service.BusinessQueryService;
import com.comiccon.service.CrudService;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static final CrudService crudDemo = new CrudService();
    private static final BusinessQueryService bizQuery = new BusinessQueryService();

    public static void main(String[] args)  {

        System.out.println("=== JDBC Comiccon (Java 21 · PostgreSQL 17 · HikariCP) ===\n");

        try {
            SchemaInitializer.initialize();
            System.out.println("БД готова.\n");
        } catch (SQLException e) {
            System.err.println("Ошибка инициализации: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        try {
            crudDemo.readArtists();
            crudDemo.readCriterions();
            crudDemo.readFest();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}