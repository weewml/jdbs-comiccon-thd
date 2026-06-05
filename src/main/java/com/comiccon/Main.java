package com.comiccon;

import com.comiccon.db.SchemaInitializer;
import com.comiccon.service.BusinessQueryService;
import com.comiccon.service.CrudService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private static final CrudService crud = new CrudService();
    private static final BusinessQueryService bizQuery = new BusinessQueryService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)  {

        System.out.println("=== JDBC Comiccon (Java 21 · PostgreSQL 17 · HikariCP) ===\n");

        try {
            SchemaInitializer.initialize();
            System.out.println("БД готова.\n");
        } catch (SQLException e) {
            System.err.println("Ошибка инициализации: " + e.getMessage());
            return;
        }

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Выберите действие: ");
            try {
                switch (choice) {
                    case 0 -> running = false;
                    case 1 -> showAllTables();
                    case 2 -> showJoinQueries();
                    case 3 -> searchMenu();
                    case 4 -> updateMenu();
                    case 5 -> deleteMenu();
                    default -> System.out.println("Неверный ввод");
                }
            } catch (SQLException e) {
                System.err.println("Ошибка БД: " + e.getMessage());
            }
        }
        System.out.println("Программа завершена");
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        System.out.println("1. Показать все таблицы");
        System.out.println("2. JOIN-запросы");
        System.out.println("3. Найти запись по ID");
        System.out.println("4. Обновить запись");
        System.out.println("5. Удалить запись по ID");
        System.out.println("0. Выход");
    }

    private static void showAllTables() throws SQLException {
        crud.readArtists();
        crud.readCriterions();
        crud.readFest();
        crud.readJurys();
        crud.readMarks();
        crud.readMembers();
        crud.readPerformances();
        crud.readShops();
        crud.readVolunteers();
    }

    private static void showJoinQueries() throws SQLException {
        bizQuery.readShopsWithArtists();
        bizQuery.readPerformancesWithMembers();
        bizQuery.readMarksWithDetails();
        bizQuery.readFestWithDetails();
    }

    private static void searchMenu() throws SQLException {
        System.out.println("\n=== ПОИСК ЗАПИСИ ПО ID ===");
        System.out.println("1. Художник    2. Критерии    3. Мероприятие    4. Жюри");
        System.out.println("5. Оценка      6. Участник    7. Выступление     8. Стенд-компания");
        System.out.println("9. Волонтер    0. Назад");
        int type = readInt("Выберите тип: ");
        if (type == 0) return;

        int id = readInt("Введите ID записи: ");

        switch (type) {
            case 1 -> crud.findArtistById(id);
            case 2 -> crud.findCriterionById(id);
            case 3 -> crud.findFestById(id);
            case 4 -> crud.findJuryById(id);
            case 5 -> crud.findMarkById(id);
            case 6 -> crud.findMemberById(id);
            case 7 -> crud.findPerformanceById(id);
            case 8 -> crud.findShopById(id);
            case 9 -> crud.findVolunteerById(id);
            default -> System.out.println("Неверный выбор");
        }
    }

    private static void updateMenu() throws SQLException {
        System.out.println("\n=== ОБНОВЛЕНИЕ ЗАПИСИ ===");
        System.out.println("1. Художник    2. Критерии    3. Мероприятие    4. Жюри");
        System.out.println("5. Оценка      6. Участник    7. Выступление     8. Стенд-компания");
        System.out.println("9. Волонтер    0. Назад");
        int type = readInt("Выберите тип: ");
        if (type == 0) return;

        int id = readInt("Введите ID записи: ");

        // Выводим текущую запись
        switch (type) {
            case 1 -> crud.findArtistById(id);
            case 2 -> crud.findCriterionById(id);
            case 3 -> crud.findFestById(id);
            case 4 -> crud.findJuryById(id);
            case 5 -> crud.findMarkById(id);
            case 6 -> crud.findMemberById(id);
            case 7 -> crud.findPerformanceById(id);
            case 8 -> crud.findShopById(id);
            case 9 -> crud.findVolunteerById(id);
            default -> {
                System.out.println("Неверный выбор");
                return;
            }
        }

        System.out.println("\nВведите новые значения (Enter - оставить без изменений):");
        switch (type) {
            case 1 -> {
                String lastName = readStringNullable("Фамилия: ");
                String firstName = readStringNullable("Имя: ");
                String patronymic = readStringNullable("Отчество: ");
                crud.updateArtist(id, lastName, firstName, patronymic);
            }
            case 2 -> {
                Double difficult = readDoubleNullable("Сложность (0.00-10.00): ");
                Double artistic = readDoubleNullable("Артистизм (0.00-10.00): ");
                crud.updateCriterion(id, difficult, artistic);
            }
            case 3 -> {
                Integer companyId = readIntNullable("ID компании: ");
                Integer performanceId = readIntNullable("ID выступления: ");
                Integer volunteerId = readIntNullable("ID волонтера: ");
                String address = readStringNullable("Адрес: ");
                LocalDate date = readLocalDateNullable("Дата (ГГГГ-ММ-ДД): ");
                crud.updateFest(id, companyId, performanceId, volunteerId, address, date);
            }
            case 4 -> {
                String lastName = readStringNullable("Фамилия: ");
                String firstName = readStringNullable("Имя: ");
                String patronymic = readStringNullable("Отчество: ");
                crud.updateJury(id, lastName, firstName, patronymic);
            }
            case 5 -> {
                Integer juryId = readIntNullable("ID жюри: ");
                Integer criterionId = readIntNullable("ID критериев: ");
                Integer performanceId = readIntNullable("ID выступления: ");
                crud.updateMark(id, juryId, criterionId, performanceId);
            }
            case 6 -> {
                String lastName = readStringNullable("Фамилия: ");
                String firstName = readStringNullable("Имя: ");
                String patronymic = readStringNullable("Отчество: ");
                String hero = readStringNullable("Герой: ");
                String source = readStringNullable("Первоисточник: ");
                crud.updateMember(id, lastName, firstName, patronymic, hero, source);
            }
            case 7 -> {
                Integer memberId = readIntNullable("ID участника: ");
                String nomination = readStringNullable("Номинация: ");
                String topic = readStringNullable("Тема: ");
                crud.updatePerformance(id, memberId, nomination, topic);
            }
            case 8 -> {
                Integer artistId = readIntNullable("ID художника: ");
                String companyName = readStringNullable("Название компании: ");
                Integer area = readIntNullable("Площадь: ");
                Integer numberTable = readIntNullable("Номер стола: ");
                crud.updateShop(id, artistId, companyName, area, numberTable);
            }
            case 9 -> {
                String lastName = readStringNullable("Фамилия: ");
                String firstName = readStringNullable("Имя: ");
                String patronymic = readStringNullable("Отчество: ");
                String task = readStringNullable("Задание: ");
                crud.updateVolunteer(id, lastName, firstName, patronymic, task);
            }
        }
    }

    private static void deleteMenu() throws SQLException {
        System.out.println("\n=== УДАЛЕНИЕ ЗАПИСИ ПО ID ===");
        System.out.println("1. Художник    2. Критерии    3. Мероприятие    4. Жюри");
        System.out.println("5. Оценка      6. Участник    7. Выступление     8. Стенд-компания");
        System.out.println("9. Волонтер    0. Назад");
        int type = readInt("Выберите тип: ");
        if (type == 0) return;

        int id = readInt("Введите ID записи: ");

        switch (type) {
            case 1 -> crud.findArtistById(id);
            case 2 -> crud.findCriterionById(id);
            case 3 -> crud.findFestById(id);
            case 4 -> crud.findJuryById(id);
            case 5 -> crud.findMarkById(id);
            case 6 -> crud.findMemberById(id);
            case 7 -> crud.findPerformanceById(id);
            case 8 -> crud.findShopById(id);
            case 9 -> crud.findVolunteerById(id);
            default -> {
                System.out.println("Неверный выбор");
                return;
            }
        }

        System.out.print("Вы уверены, что хотите удалить эту запись? (Да/Нет): ");
        String confirm = scanner.nextLine();
        if (!confirm.equalsIgnoreCase("Да")) {
            System.out.println("Удаление отменено");
            return;
        }

        switch (type) {
            case 1 -> crud.deleteArtist(id);
            case 2 -> crud.deleteCriterion(id);
            case 3 -> crud.deleteFest(id);
            case 4 -> crud.deleteJury(id);
            case 5 -> crud.deleteMark(id);
            case 6 -> crud.deleteMember(id);
            case 7 -> crud.deletePerformance(id);
            case 8 -> crud.deleteShop(id);
            case 9 -> crud.deleteVolunteer(id);
        }
    }

    // методы для парсинга ввода
    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Ошибка, введите число: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    private static String readStringNullable(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine();
        return line.isBlank() ? null : line;
    }

    private static Integer readIntNullable(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine();
        if (line.isBlank()) return null;
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка, введите целое число или оставьте пустым");
            return readIntNullable(prompt);
        }
    }

    private static Double readDoubleNullable(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine();
        if (line.isBlank()) return null;
        try {
            return Double.parseDouble(line);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка, введите число (например, 7.5) или оставьте пустым");
            return readDoubleNullable(prompt);
        }
    }

    private static LocalDate readLocalDateNullable(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine();
        if (line.isBlank()) return null;
        try {
            return LocalDate.parse(line);
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Ошибка, введите дату в формате ГГГГ-ММ-ДД (например, 2025-12-31) или оставьте пустым");
            return readLocalDateNullable(prompt);
        }
    }
}