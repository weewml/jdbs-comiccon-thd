-- Обновление фамилии участника по айди с возвращением
UPDATE comiccon.members
	SET last_name = 'Жидкин'
	WHERE member_id  = 5
	RETURNING *;

-- Обновление критерия сложности костюма по айди с возвращением
UPDATE comiccon.criterions
	SET difficult_mark = difficult_mark * 1.1
	WHERE criterion_id = 1
	RETURNING *;

-- Обновление мероприятия - волонтера, адреса и даты по айди мероприятия с возвращением
UPDATE comiccon.fest
	SET volunteer_id = null,
		address = 'Ул. Образцова 10',
		date = '2026-04-10 17:00:00'
	WHERE event_id = 1
	RETURNING *;

-- Обновление прикрепленного волонтера к мероприятию - по адресу проведения мероприятия с возвращением
UPDATE comiccon.fest
	SET volunteer_id = null
	WHERE address = 'Ул. Образцова 9'
	RETURNING *;

-- Удаление художника по айди с возвращением
DELETE FROM comiccon.artists
	WHERE artist_id = 4
	RETURNING *;

-- Удаление компании по условию по размеру площади с возвращением
DELETE FROM comiccon.shops
	WHERE area > 50
	RETURNING *;

-- Удаление записи в род таблице с возвращением
DELETE FROM comiccon.shops
	WHERE artist_id = 5
	RETURNING *;


-- УЧАСТНИКИ
-- Вывод всех участников
SELECT * FROM comiccon.members;

-- Все участники с источником "Marvel"
SELECT * FROM comiccon.members
	WHERE original_source = 'Marvel';


-- ХУДОЖНИКИ
-- Вывод всех художников
SELECT * FROM comiccon.artists;


-- ЖЮРИ
-- Вывод всех жюри
SELECT * FROM comiccon.jurys;


-- КРИТЕРИИ
-- Вывод всех критериев
SELECT * FROM comiccon.criterions;


-- ВОЛОНТЕРЫ
-- Вывод всех волонтеров
SELECT * FROM comiccon.volunteers;


-- СТЕНДЫ-МАГАЗИНЫ
-- Вывод всех магазинов-стендов
SELECT * FROM comiccon.shops;

-- Вывод всех магазинов-стендов с join (c фамилией и именем художников)
SELECT artists.last_name, artists.first_name, company_name, area, number_table FROM comiccon.shops
	JOIN comiccon.artists ON artists.artist_id = shops.artist_id;

-- Все стенды-магазины с площадью от 25 и до 45
SELECT * FROM comiccon.shops
	WHERE area BETWEEN 25 AND 45;


-- ВЫСТУПЛЕНИЯ
-- Вывод всех выступлений
SELECT * FROM comiccon.performances;

-- Вывод всех выступлений с join (c фамилией, именем и персонажем участника)
SELECT members.last_name, members.first_name, members.hero, nomination, topic FROM comiccon.performances
	JOIN comiccon.members ON members.member_id = performances.member_id;


-- ОЦЕНКИ
-- Вывод всех оценок
SELECT * FROM comiccon.marks;

-- Вывод всех оценок с join (c фамилией жюри, оценке за сложность и артистизм и фамилией и персонажем участника)
SELECT performances.topic, members.last_name, members.hero,
		criterions.difficult_mark, criterions.artistic_mark,
		jurys.last_name
		FROM comiccon.marks
	JOIN comiccon.performances ON performances.performance_id = marks.performance_id
	JOIN comiccon.members ON members.member_id = performances.member_id
	JOIN comiccon.criterions ON criterions.criterion_id = marks.criterion_id
	JOIN comiccon.jurys ON jurys.jury_id = marks.jury_id;


-- ФЕСТИВАЛЬ
-- Вывод всех записей фестиваля
SELECT * FROM comiccon.fest;

-- Вывод всех записей фестиваля с join (c компанией, представлением, волонтером)
SELECT shops.company_name, performances.topic,
		volunteers.last_name as volunteer,
		volunteers.task as volunteer_task, address, date FROM comiccon.fest
	JOIN comiccon.performances ON performances.performance_id = fest.performance_id
	JOIN comiccon.shops ON shops.company_id  = fest.company_id
	JOIN comiccon.volunteers ON volunteers.volunteer_id = fest.volunteer_id;



-- Вывод всех выступлений с join (c фамилией, именем и персонажем участника)
SELECT
		m.last_name as "Фамилия",
		m.first_name as "Имя",
		m.hero as "Герой",
		p.nomination as "Номинация",
		p.topic as "Тема"
	FROM comiccon.performances AS p
	JOIN comiccon.members AS m ON m.member_id = p.member_id;


-- Вывод всех оценок с join (c фамилией жюри, оценке за сложность и артистизм и фамилией и персонажем участника)
SELECT
		p.topic as "Тема",
		m.last_name as "Фамилия участника",
		m.hero as "Герой",
		c.difficult_mark as "Сложность костюма",
		c.artistic_mark as "Артистичность",
		j.last_name as "Фамилия жюри"
	FROM comiccon.marks AS mr
	JOIN comiccon.performances AS p ON p.performance_id = mr.performance_id
	JOIN comiccon.members AS m ON m.member_id = p.member_id
	JOIN comiccon.criterions AS c ON c.criterion_id = mr.criterion_id
	JOIN comiccon.jurys AS j ON j.jury_id = mr.jury_id;


-- Вывод всех записей фестиваля с join (c компанией, представлением, волонтером)
SELECT
		s.company_name as "Название компании",
		p.topic as "Тема",
		v.last_name as "Волонтер",
		v.task as "Задание волонтера",
		f.address as "Адрес",
		f.date as "Дата"
	FROM comiccon.fest AS f
	JOIN comiccon.performances AS p ON p.performance_id = f.performance_id
	JOIN comiccon.shops AS s ON s.company_id  = f.company_id
	JOIN comiccon.volunteers AS v ON v.volunteer_id = f.volunteer_id;



-- LEFT JOIN (все из левой + совпадения) - все из fest с выводом волонтеров есть/нет его
SELECT
		v.last_name as "Фамилия",
		v.first_name as "Имя",
		v.task as "Задание волонтера",
		s.company_name as "Название компании",
		p.topic as "Тема выступления",
		m.hero as "Герой",
		f.date as "Дата"
	FROM comiccon.fest AS f
	JOIN comiccon.performances AS p ON p.performance_id = f.performance_id
	JOIN comiccon.shops AS s ON s.company_id  = f.company_id
	JOIN comiccon.members AS m ON m.member_id = p.member_id
	LEFT JOIN comiccon.volunteers AS v ON v.volunteer_id = f.volunteer_id;


-- LEFT JOIN (записи без связей) - незадействованные волонтеры нигде
SELECT
		v.last_name as "Фамилия",
		v.first_name as "Имя",
		v.task as "Задание волонтера",
		f.volunteer_id as "Прикрепление к мероприятию"
	FROM comiccon.volunteers AS v
	LEFT JOIN comiccon.fest AS f ON v.volunteer_id = f.volunteer_id
	WHERE f.volunteer_id IS NULL;


-- FULL OUTER JOIN мероприятий и волонтеров
SELECT
		v.last_name as "Фамилия",
		v.first_name as "Имя",
		v.task as "Задание волонтера",
		f.volunteer_id as "Прикрепление к мероприятию",
		f.date as "Дата"
	FROM comiccon.volunteers AS v
	FULL OUTER JOIN comiccon.fest AS f ON v.volunteer_id = f.volunteer_id;


-- Запрос выводит значения площади больше среднего с использование подзапроса AVG
SELECT company_name, area FROM comiccon.shops
	WHERE area > (SELECT AVG(area) FROM comiccon.shops);


-- Запрос выводит дату и адрес через название компании с подзапросом
SELECT date, address, company_id FROM comiccon.fest
	WHERE company_id IN (SELECT company_id FROM comiccon.shops
							WHERE company_name = 'Авито');


-- Волонтеры которые привязаны к мероприятиям
SELECT
		v.volunteer_id as "Айди волонтера",
		v.last_name as "Фамилия",
		v.first_name as "Имя",
		v.task as "Задание волонтера"
	FROM comiccon.volunteers AS v
	WHERE EXISTS (SELECT 1 FROM comiccon.fest f WHERE f.volunteer_id = v.volunteer_id);


-- Скалярный запрос без JOIN (дата мероприятия + фамилия волонтера)
SELECT
		f.date as "Дата",
		(SELECT v.last_name
			FROM comiccon.volunteers as v
			WHERE v.volunteer_id = f.volunteer_id
		) as "Фамилия волонтера"
	FROM comiccon.fest AS f;
