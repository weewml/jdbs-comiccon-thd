package com.comiccon.service;

import com.comiccon.dao.*;
import com.comiccon.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class CrudService {

    private final ArtistsDao artistsDao = new ArtistsDao();
    private final CriterionsDao criterionsDao = new CriterionsDao();
    private final FestDao festDao = new FestDao();
    private final JurysDao jurysDao = new JurysDao();
    private final MarksDao marksDao = new MarksDao();
    private final MembersDao membersDao = new MembersDao();
    private final PerformancesDao performancesDao = new PerformancesDao();
    private final ShopsDao shopsDao = new ShopsDao();
    private final VolunteersDao volunteersDao = new VolunteersDao();

    // READ
    // Чтение художников
    public void readArtists() throws SQLException {
        System.out.println("=== READ artists ===");

        System.out.printf("%-5s %-12s %-10s %-12s%n", "ID", "Фамилия", "Имя", "Отчество");
        for (Artists a : artistsDao.findAll()) {
            System.out.printf("%-5s %-12s %-10s %-12s%n",
                    a.getArtistId(), a.getLastName(), a.getFirstName(), a.getPatronymic());
        }

        System.out.println();
    }

    // Поиск художника по id
    public void findArtistById(int id) throws SQLException {
        System.out.printf("\nПоиск художника по id=%d\n", id);
        artistsDao.findById(id).ifPresentOrElse(
                a -> System.out.println(a),
                () -> System.out.println("Не найден"));

        System.out.println();
    }

    // Чтение критериев
    public void readCriterions() throws SQLException {
        System.out.println("=== READ criterions ===");

        System.out.printf("%-5s %-10s %-10s%n", "ID", "Сложность", "Артистичность");
        for (Criterions c : criterionsDao.findAll()) {
            System.out.printf("%-5s %-10s %-10s%n",
                    c.getCriterionId(), c.getDifficultMark(), c.getArtisticMark());
        }

        System.out.println();
    }

    // Поиск критериев оценивания по id
    public void findCriterionById(int id) throws SQLException {
        System.out.printf("\nПоиск критериев оценивания по id=%d\n", id);
        criterionsDao.findById(id).ifPresentOrElse(
                с -> System.out.println(с),
                () -> System.out.println("Не найден"));

        System.out.println();
    }

    // Чтение мероприятий фестиваля
    public void readFest() throws SQLException {
        System.out.println("=== READ fest ===");

        System.out.printf("%-5s %-8s %-8s %-8s %-20s %-15s%n",
                "ID", "ID-комп", "ID-выст", "ID-вол", "Адрес", "Дата");
        for (Fest f : festDao.findAll()) {
            System.out.printf("%-5s %-8s %-8s %-8s %-20s %-15s%n",
                    f.getEventId(), f.getCompanyId(), f.getPerformanceId(),
                    f.getVolunteerId(), f.getAddress(), f.getDate());
        }

        System.out.println();
    }

    // Поиск мероприятия по id
    public void findFestById(int id) throws SQLException {
        System.out.printf("\nПоиск мероприятия по id=%d\n", id);
        festDao.findById(id).ifPresentOrElse(
                f -> System.out.println(f),
                () -> System.out.println("Не найден"));

        System.out.println();
    }

    // Чтение жюри
    public void readJurys() throws SQLException {
        System.out.println("=== READ jurys ===");

        System.out.printf("%-5s %-12s %-10s %-12s%n", "ID", "Фамилия", "Имя", "Отчество");
        for (Jurys j : jurysDao.findAll()) {
            System.out.printf("%-5s %-12s %-10s %-12s%n",
                    j.getJuryId(), j.getLastName(), j.getFirstName(), j.getPatronymic());
        }

        System.out.println();
    }

    // Поиск жюри по id
    public void findJuryById(int id) throws SQLException {
        System.out.printf("\nПоиск жюри по id=%d\n", id);
        jurysDao.findById(id).ifPresentOrElse(
                j -> System.out.println(j),
                () -> System.out.println("Не найден"));

        System.out.println();
    }


    // Чтение оценок
    public void readMarks() throws SQLException {
        System.out.println("=== READ marks ===");

        System.out.printf("%-5s %-8s %-8s %-8s%n",
                "ID", "ID-жюри", "ID-крит", "ID-выст");
        for (Marks m : marksDao.findAll()) {
            System.out.printf("%-5s %-8s %-8s %-8s%n",
                    m.getMarkId(), m.getJuryId(), m.getCriterionId(), m.getPerformanceId());
        }

        System.out.println();
    }

    // Поиск оценки по id
    public void findMarkById(int id) throws SQLException {
        System.out.printf("\nПоиск оценки по id=%d\n", id);
        marksDao.findById(id).ifPresentOrElse(
                m -> System.out.println(m),
                () -> System.out.println("Не найден"));

        System.out.println();
    }

    // Чтение участников
    public void readMembers() throws SQLException {
        System.out.println("=== READ members ===");

        System.out.printf("%-5s %-12s %-10s %-12s %-18s %-12s%n",
                "ID", "Фамилия", "Имя", "Отчество", "Герой", "Первоисточник");
        for (Members m : membersDao.findAll()) {
            System.out.printf("%-5s %-12s %-10s %-12s %-18s %-12s%n",
                    m.getMemberId(), m.getLastName(), m.getFirstName(), m.getPatronymic(),
                    m.getHero(), m.getOriginalSource());
        }

        System.out.println();
    }

    // Поиск участника по id
    public void findMemberById(int id) throws SQLException {
        System.out.printf("\nПоиск участника по id=%d\n", id);
        membersDao.findById(id).ifPresentOrElse(
                m -> System.out.println(m),
                () -> System.out.println("Не найден"));

        System.out.println();
    }

    // Чтение выступлений
    public void readPerformances() throws SQLException {
        System.out.println("=== READ performances ===");

        System.out.printf("%-5s %-10s %-12s %-12s%n",
                "ID", "ID-участ", "Номинация", "Тематика");
        for (Performances p : performancesDao.findAll()) {
            System.out.printf("%-5s %-10s %-12s %-12s%n",
                    p.getPerformanceId(), p.getMemberId(), p.getNomination(), p.getTopic());
        }

        System.out.println();
    }

    // Поиск выступления по id
    public void findPerformanceById(int id) throws SQLException {
        System.out.printf("\nПоиск выступления по id=%d\n", id);
        performancesDao.findById(id).ifPresentOrElse(
                p -> System.out.println(p),
                () -> System.out.println("Не найден"));

        System.out.println();
    }

    // Чтение магазинов-стендов компаний
    public void readShops() throws SQLException {
        System.out.println("=== READ shops ===");

        System.out.printf("%-5s %-12s %-18s %-12s %-12s%n",
                "ID", "ID-худож", "Название компании", "Площадь", "Номер стола");
        for (Shops s : shopsDao.findAll()) {
            System.out.printf("%-5s %-12s %-18s %-12s %-12s%n",
                    s.getCompanyId(), s.getArtistId(), s.getCompanyName(), s.getArea(), s.getNumberTable());
        }

        System.out.println();
    }

    // Поиск магазина-стенда компании по id
    public void findShopById(int id) throws SQLException {
        System.out.printf("\nПоиск магазина-стенда компании по id=%d\n", id);
        shopsDao.findById(id).ifPresentOrElse(
                s -> System.out.println(s),
                () -> System.out.println("Не найден"));

        System.out.println();
    }

    // Чтение волонтеров
    public void readVolunteers() throws SQLException {
        System.out.println("=== READ volunteers ===");

        System.out.printf("%-5s %-12s %-10s %-12s %-12s%n", "ID", "Фамилия", "Имя", "Отчество", "Задание");
        for (Volunteers v : volunteersDao.findAll()) {
            System.out.printf("%-5s %-12s %-10s %-12s %-12s%n",
                    v.getVolunteerId(), v.getLastName(), v.getFirstName(), v.getPatronymic(), v.getTask());
        }

        System.out.println();
    }

    // Поиск волонтера по id
    public void findVolunteerById(int id) throws SQLException {
        System.out.printf("\nПоиск волонтера по id=%d\n", id);
        volunteersDao.findById(id).ifPresentOrElse(
                v -> System.out.println(v),
                () -> System.out.println("Не найден"));

        System.out.println();
    }

    // СREATE

    public void createArtist(Artists artist) throws SQLException {
        int id = artistsDao.insert(artist);
        System.out.println("Создан художник с id=" + id);
    }

    public void createCriterion(Criterions criterion) throws SQLException {
        int id = criterionsDao.insert(criterion);
        System.out.println("Созданы критерии с id=" + id);
    }

    public void createFest(Fest fest) throws SQLException {
        int id = festDao.insert(fest);
        System.out.println("Создано мероприятие с id=" + id);
    }

    public void createJury(Jurys jury) throws SQLException {
        int id = jurysDao.insert(jury);
        System.out.println("Создано жюри с id=" + id);
    }

    public void createMark(Marks mark) throws SQLException {
        int id = marksDao.insert(mark);
        System.out.println("Создана оценка с id=" + id);
    }

    public void createMember(Members member) throws SQLException {
        int id = membersDao.insert(member);
        System.out.println("Создан участник с id=" + id);
    }

    public void createPerformance(Performances performance) throws SQLException {
        int id = performancesDao.insert(performance);
        System.out.println("Создано выступление с id=" + id);
    }

    public void createShop(Shops shop) throws SQLException {
        int id = shopsDao.insert(shop);
        System.out.println("Создан стенд-компания с id=" + id);
    }

    public void createVolunteer(Volunteers volunteer) throws SQLException {
        int id = volunteersDao.insert(volunteer);
        System.out.println("Создан волонтер с id=" + id);
    }

// UPDATE

    public void updateArtist(int id, String lastName, String firstName, String patronymic) throws SQLException {
        Optional<Artists> opt = artistsDao.findById(id);
        if (opt.isEmpty()) {
            System.out.printf("Художник с id=%d не найден\n", id);
            return;
        }
        Artists a = opt.get();
        StringBuilder changes = new StringBuilder();
        if (lastName != null) {
            changes.append(String.format("фамилия '%s'->'%s' ", a.getLastName(), lastName));
            a.setLastName(lastName);
        }
        if (firstName != null) {
            changes.append(String.format("имя '%s'->'%s' ", a.getFirstName(), firstName));
            a.setFirstName(firstName);
        }
        if (patronymic != null) {
            changes.append(String.format("отчество '%s'->'%s' ", a.getPatronymic(), patronymic));
            a.setPatronymic(patronymic);
        }
        boolean ok = artistsDao.update(a);
        System.out.printf("Обновлен художник id=%d: %s(успех=%b)%n", id, changes, ok);
    }

    public void updateCriterion(int id, Double difficultMark, Double artisticMark) throws SQLException {
        Optional<Criterions> opt = criterionsDao.findById(id);
        if (opt.isEmpty()) {
            System.out.printf("Критерии с id=%d не найдены\n", id);
            return;
        }
        Criterions c = opt.get();
        StringBuilder changes = new StringBuilder();
        if (difficultMark != null) {
            changes.append(String.format("сложность %.2f->%.2f ", c.getDifficultMark(), difficultMark));
            c.setDifficultMark(difficultMark);
        }
        if (artisticMark != null) {
            changes.append(String.format("артистизм %.2f->%.2f ", c.getArtisticMark(), artisticMark));
            c.setArtisticMark(artisticMark);
        }
        boolean ok = criterionsDao.update(c);
        System.out.printf("Обновлены критерии id=%d: %s(успех=%b)%n", id, changes, ok);
    }

    public void updateFest(int id, Integer companyId, Integer performanceId, Integer volunteerId,
                           String address, LocalDate date) throws SQLException {
        Optional<Fest> opt = festDao.findById(id);
        if (opt.isEmpty()) {
            System.out.printf("Мероприятие с id=%d не найдено\n", id);
            return;
        }
        Fest f = opt.get();
        StringBuilder changes = new StringBuilder();
        if (companyId != null) {
            changes.append(String.format("компания %d->%d ", f.getCompanyId(), companyId));
            f.setCompanyId(companyId);
        }
        if (performanceId != null) {
            changes.append(String.format("выступление %d->%d ", f.getPerformanceId(), performanceId));
            f.setPerformanceId(performanceId);
        }
        if (volunteerId != null) {
            changes.append(String.format("волонтер %d->%d ", f.getVolunteerId(), volunteerId));
            f.setVolunteerId(volunteerId);
        }
        if (address != null) {
            changes.append(String.format("адрес '%s'->'%s' ", f.getAddress(), address));
            f.setAddress(address);
        }
        if (date != null) {
            changes.append(String.format("дата %s->%s ", f.getDate(), date));
            f.setDate(date);
        }
        boolean ok = festDao.update(f);
        System.out.printf("Обновлено мероприятие id=%d: %s(успех=%b)%n", id, changes, ok);
    }

    public void updateJury(int id, String lastName, String firstName, String patronymic) throws SQLException {
        Optional<Jurys> opt = jurysDao.findById(id);
        if (opt.isEmpty()) {
            System.out.printf("Жюри с id=%d не найдено\n", id);
            return;
        }
        Jurys j = opt.get();
        StringBuilder changes = new StringBuilder();
        if (lastName != null) {
            changes.append(String.format("фамилия '%s'->'%s' ", j.getLastName(), lastName));
            j.setLastName(lastName);
        }
        if (firstName != null) {
            changes.append(String.format("имя '%s'->'%s' ", j.getFirstName(), firstName));
            j.setFirstName(firstName);
        }
        if (patronymic != null) {
            changes.append(String.format("отчество '%s'->'%s' ", j.getPatronymic(), patronymic));
            j.setPatronymic(patronymic);
        }
        boolean ok = jurysDao.update(j);
        System.out.printf("Обновлено жюри id=%d: %s(успех=%b)%n", id, changes, ok);
    }

    public void updateMark(int id, Integer juryId, Integer criterionId, Integer performanceId) throws SQLException {
        Optional<Marks> opt = marksDao.findById(id);
        if (opt.isEmpty()) {
            System.out.printf("Оценка с id=%d не найдена\n", id);
            return;
        }
        Marks m = opt.get();
        StringBuilder changes = new StringBuilder();
        if (juryId != null) {
            changes.append(String.format("жюри %d->%d ", m.getJuryId(), juryId));
            m.setJuryId(juryId);
        }
        if (criterionId != null) {
            changes.append(String.format("критерии %d->%d ", m.getCriterionId(), criterionId));
            m.setCriterionId(criterionId);
        }
        if (performanceId != null) {
            changes.append(String.format("выступление %d->%d ", m.getPerformanceId(), performanceId));
            m.setPerformanceId(performanceId);
        }
        boolean ok = marksDao.update(m);
        System.out.printf("Обновлена оценка id=%d: %s(успех=%b)%n", id, changes, ok);
    }

    public void updateMember(int id, String lastName, String firstName, String patronymic,
                             String hero, String originalSource) throws SQLException {
        Optional<Members> opt = membersDao.findById(id);
        if (opt.isEmpty()) {
            System.out.printf("Участник с id=%d не найден\n", id);
            return;
        }
        Members m = opt.get();
        StringBuilder changes = new StringBuilder();
        if (lastName != null) {
            changes.append(String.format("фамилия '%s'->'%s' ", m.getLastName(), lastName));
            m.setLastName(lastName);
        }
        if (firstName != null) {
            changes.append(String.format("имя '%s'->'%s' ", m.getFirstName(), firstName));
            m.setFirstName(firstName);
        }
        if (patronymic != null) {
            changes.append(String.format("отчество '%s'->'%s' ", m.getPatronymic(), patronymic));
            m.setPatronymic(patronymic);
        }
        if (hero != null) {
            changes.append(String.format("герой '%s'->'%s' ", m.getHero(), hero));
            m.setHero(hero);
        }
        if (originalSource != null) {
            changes.append(String.format("источник '%s'->'%s' ", m.getOriginalSource(), originalSource));
            m.setOriginalSource(originalSource);
        }
        boolean ok = membersDao.update(m);
        System.out.printf("Обновлен участник id=%d: %s(успех=%b)%n", id, changes, ok);
    }

    public void updatePerformance(int id, Integer memberId, String nomination, String topic) throws SQLException {
        Optional<Performances> opt = performancesDao.findById(id);
        if (opt.isEmpty()) {
            System.out.printf("Выступление с id=%d не найдено\n", id);
            return;
        }
        Performances p = opt.get();
        StringBuilder changes = new StringBuilder();
        if (memberId != null) {
            changes.append(String.format("участник %d->%d ", p.getMemberId(), memberId));
            p.setMemberId(memberId);
        }
        if (nomination != null) {
            changes.append(String.format("номинация '%s'->'%s' ", p.getNomination(), nomination));
            p.setNomination(nomination);
        }
        if (topic != null) {
            changes.append(String.format("тема '%s'->'%s' ", p.getTopic(), topic));
            p.setTopic(topic);
        }
        boolean ok = performancesDao.update(p);
        System.out.printf("Обновлено выступление id=%d: %s(успех=%b)%n", id, changes, ok);
    }

    public void updateShop(int id, Integer artistId, String companyName, Integer area, Integer numberTable) throws SQLException {
        Optional<Shops> opt = shopsDao.findById(id);
        if (opt.isEmpty()) {
            System.out.printf("Стенд-компания с id=%d не найден\n", id);
            return;
        }
        Shops s = opt.get();
        StringBuilder changes = new StringBuilder();
        if (artistId != null) {
            changes.append(String.format("художник %d->%d ", s.getArtistId(), artistId));
            s.setArtistId(artistId);
        }
        if (companyName != null) {
            changes.append(String.format("название '%s'->'%s' ", s.getCompanyName(), companyName));
            s.setCompanyName(companyName);
        }
        if (area != null) {
            changes.append(String.format("площадь %d->%d ", s.getArea(), area));
            s.setArea(area);
        }
        if (numberTable != null) {
            changes.append(String.format("стол %d->%d ", s.getNumberTable(), numberTable));
            s.setNumberTable(numberTable);
        }
        boolean ok = shopsDao.update(s);
        System.out.printf("Обновлен стенд id=%d: %s(успех=%b)%n", id, changes, ok);
    }

    public void updateVolunteer(int id, String lastName, String firstName, String patronymic, String task) throws SQLException {
        Optional<Volunteers> opt = volunteersDao.findById(id);
        if (opt.isEmpty()) {
            System.out.printf("Волонтер с id=%d не найден\n", id);
            return;
        }
        Volunteers v = opt.get();
        StringBuilder changes = new StringBuilder();
        if (lastName != null) {
            changes.append(String.format("фамилия '%s'->'%s' ", v.getLastName(), lastName));
            v.setLastName(lastName);
        }
        if (firstName != null) {
            changes.append(String.format("имя '%s'->'%s' ", v.getFirstName(), firstName));
            v.setFirstName(firstName);
        }
        if (patronymic != null) {
            changes.append(String.format("отчество '%s'->'%s' ", v.getPatronymic(), patronymic));
            v.setPatronymic(patronymic);
        }
        if (task != null) {
            changes.append(String.format("задание '%s'->'%s' ", v.getTask(), task));
            v.setTask(task);
        }
        boolean ok = volunteersDao.update(v);
        System.out.printf("Обновлен волонтер id=%d: %s(успех=%b)%n", id, changes, ok);
    }

// DELETE

    public void deleteArtist(int id) throws SQLException {
        if (artistsDao.delete(id)) {
            System.out.println("Художник удален");
        } else { System.out.println("Художник не найден"); }
    }

    public void deleteCriterion(int id) throws SQLException {
        if (criterionsDao.delete(id)) {
            System.out.println("Критерии удалены");
        } else { System.out.println("Критерии не найдены"); }
    }

    public void deleteFest(int id) throws SQLException {
        if (festDao.delete(id)) {
            System.out.println("Мероприятие удалено");
        } else { System.out.println("Мероприятие не найдено"); }
    }

    public void deleteJury(int id) throws SQLException {
        if (jurysDao.delete(id)) {
            System.out.println("Жюри удалено");
        } else { System.out.println("Жюри не найдено"); }
    }

    public void deleteMark(int id) throws SQLException {
        if (marksDao.delete(id)) {
            System.out.println("Оценка удалена");
        } else { System.out.println("Оценка не найдена"); }
    }

    public void deleteMember(int id) throws SQLException {
        if (membersDao.delete(id)) {
            System.out.println("Участник удален");
        } else { System.out.println("Участник не найден"); }
    }

    public void deletePerformance(int id) throws SQLException {
        if (performancesDao.delete(id)) {
            System.out.println("Выступление удалено");
        } else { System.out.println("Выступление не найдено"); }
    }

    public void deleteShop(int id) throws SQLException {
        if (shopsDao.delete(id)) {
            System.out.println("Стенд-компания удален");
        } else { System.out.println("Стенд-компания не найден"); }
    }

    public void deleteVolunteer(int id) throws SQLException {
        if (volunteersDao.delete(id)) {
            System.out.println("Волонтер удален");
        } else { System.out.println("Волонтер не найден"); }
    }

}
