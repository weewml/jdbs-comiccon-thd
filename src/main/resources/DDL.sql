-- https://cloud.mail.ru/public/c4a2/bJ58Wh6yz
-- docker run --name lab-psql -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres

CREATE SCHEMA IF NOT EXISTS comiccon;

-- независимые таблицы (без FK)
CREATE TABLE comiccon.members (
	member_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	last_name VARCHAR(40) NOT NULL,
	first_name VARCHAR(40) NOT NULL,
	patronymic VARCHAR(40) NULL,
	hero VARCHAR(100) NOT NULL,
	original_source VARCHAR(200) NOT NULL
);


CREATE TABLE comiccon.jurys (
	jury_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	last_name VARCHAR(40) NOT NULL,
	first_name VARCHAR(40) NOT NULL,
	patronymic VARCHAR(40) NULL
);


CREATE TABLE comiccon.criterions (
	criterion_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	difficult_mark NUMERIC(4,2) NOT NULL,
	artistic_mark NUMERIC(4,2) NOT NULL,

	CONSTRAINT chk_criterions_difficult CHECK (0 <= difficult_mark AND difficult_mark <= 10),
	CONSTRAINT chk_criterions_artistic CHECK (0 <= artistic_mark AND artistic_mark <= 10)
);


CREATE TABLE comiccon.volunteers (
	volunteer_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	last_name VARCHAR(40) NOT NULL,
	first_name VARCHAR(40) NOT NULL,
	patronymic VARCHAR(40) NULL,
	task TEXT NOT NULL
);


CREATE TABLE comiccon.artists (
	artist_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	last_name VARCHAR(40) NOT NULL,
	first_name VARCHAR(40) NOT NULL,
	patronymic VARCHAR(40) NULL
);


-- Таблицы с FK на независимые
CREATE TABLE comiccon.performances (
	performance_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	member_id INT NOT NULL,
	nomination VARCHAR(30) NOT NULL,
	topic VARCHAR(250) NOT NULL,

	CONSTRAINT fk_performances_member FOREIGN KEY (member_id)
		REFERENCES comiccon.members(member_id) ON DELETE CASCADE
);

CREATE TABLE comiccon.shops (
	company_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	artist_id INT NOT NULL,
	company_name VARCHAR(300) NOT NULL,
	area INT NOT NULL,
	number_table SMALLINT NOT NULL,

	CONSTRAINT chk_shop_are CHECK (area > 0),

	CONSTRAINT fk_shops_artist FOREIGN KEY (artist_id)
		REFERENCES comiccon.artists(artist_id) ON DELETE RESTRICT
);



-- Таблицы с FK на всё вышестоящее
CREATE TABLE comiccon.marks (
	mark_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	jury_id INT NOT NULL,
	criterion_id INT NOT NULL,
	performance_id INT NOT NULL,

	CONSTRAINT fk_marks_jury FOREIGN KEY (jury_id)
		REFERENCES comiccon.jurys(jury_id) ON DELETE RESTRICT,

	CONSTRAINT fk_marks_criterion FOREIGN KEY (criterion_id)
		REFERENCES comiccon.criterions(criterion_id) ON DELETE RESTRICT,

	CONSTRAINT fk_marks_performance FOREIGN KEY (performance_id)
		REFERENCES comiccon.performances(performance_id) ON DELETE CASCADE
);


CREATE TABLE comiccon.fest (
	event_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	company_id INT NOT NULL,
	performance_id INT NULL,
	volunteer_id INT NULL,
	address VARCHAR(300) NOT NULL,
	date TIMESTAMPTZ NOT NULL,

	CONSTRAINT fk_fest_company FOREIGN KEY (company_id)
		REFERENCES comiccon.shops(company_id) ON DELETE RESTRICT,

	CONSTRAINT fk_fest_perfomance FOREIGN KEY (performance_id)
		REFERENCES comiccon.performances(performance_id) ON DELETE RESTRICT,

	CONSTRAINT fk_fest_volunteer FOREIGN KEY (volunteer_id)
		REFERENCES comiccon.volunteers(volunteer_id) ON DELETE CASCADE
);




