# Здесь разные тестовые скрипты

SELECT * FROM book b where b.genre_id in (select g.id from genre g where g.name like '%ю%');

SELECT b.name, g.name FROM book b
join genre g on b.genre_id=g.id;

SELECT b.name, a.fio, g.name, p.name FROM book b
join author a on b.author_id=a.id
join genre g on b.genre_id=g.id
join publisher p on b.publisher_id=p.id;



UPDATE book set content_type=NULL WHERE id=35;
ALTER TABLE book MODIFY COLUMN content_type varchar(450) DEFAULT 'application/pdf' NULL;

SELECT * FROM book b
  join carts_to_books ctb on b.id=ctb.book_id AND ctb.username = 'user';

SELECT * FROM book b WHERE b.id in (
  SELECT ctb.book_id FROM carts_to_books ctb where ctb.username = 'user'
);

SELECT * FROM book b WHERE b.id in (
  SELECT otb.book_id FROM orders_to_books otb where otb.order_id in (
    SELECT o.id
    FROM order_ o
    WHERE o.username = 'newUser'
  )
);


INSERT INTO content1 (book_id,content) SELECT id,content FROM book;
ALTER TABLE book DROP COLUMN content;
ALTER TABLE book add content longblob NOT NULL;
INSERT INTO book_content (book_id,content) SELECT book_id,content FROM content1;

CREATE TABLE content (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  book_id bigint(20) NOT NULL,
  content longblob NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_content FOREIGN KEY (book_id) REFERENCES book (id)
);


UPDATE book b set b.file_size=(select OCTET_LENGTH(bc.content) FROM book_content bc WHERE b.id = bc.book_id)/1000000;

ALTER TABLE book_content DROP FOREIGN KEY `fk_book_content`;

ALTER TABLE book_content
  ADD CONSTRAINT fk_book_content FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE;

# Авторизация в MySQL:
# sudo mysql --user=root --password=4
# Для выхода:
# exit
CREATE USER 'owner' IDENTIFIED BY '4';
CREATE USER 'aplib_owner' IDENTIFIED BY '4';
GRANT ALL PRIVILEGES ON *.* TO 'owner';
GRANT ALL PRIVILEGES ON aplib.* TO 'aplib_owner';
FLUSH PRIVILEGES;

-- username:admin password:q
INSERT INTO "user"(username, password, enabled) VALUES ('admin', '$2a$05$IHUj33qfJo7veMhOY06CxOEt6Y6IPZX5cB3zbxuz.EVdUvd.XNrou', TRUE );
-- username:user password:q
INSERT INTO "user"(username, password, enabled) VALUES ('user', '$2a$05$IHUj33qfJo7veMhOY06CxOEt6Y6IPZX5cB3zbxuz.EVdUvd.XNrou', TRUE );

INSERT INTO user_role(username, role) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_role(username, role) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_role(username, role) VALUES ('user', 'ROLE_USER');

INSERT INTO genre (id, name, parent) VALUES (10, 'Детектив', null);
INSERT INTO genre (id, name, parent) VALUES (11, 'Мистика', null);
INSERT INTO genre (id, name, parent) VALUES (12, 'Юмор', null);
INSERT INTO genre (id, name, parent) VALUES (13, 'Исторический', null);
INSERT INTO genre (id, name, parent) VALUES (14, 'Роман', null);
INSERT INTO genre (id, name, parent) VALUES (15, 'Сказка', null);
INSERT INTO genre (id, name, parent) VALUES (16, 'Приключения', null);
INSERT INTO genre (id, name, parent) VALUES (17, 'Бизнес', null);
INSERT INTO genre (id, name, parent) VALUES (18, 'Боевик', null);
INSERT INTO genre (id, name, parent) VALUES (19, 'Религия', null);
INSERT INTO genre (id, name, parent) VALUES (20, 'Компьютеры', null);
INSERT INTO genre (id, name, parent) VALUES (21, 'Семья', null);
INSERT INTO genre (id, name, parent) VALUES (22, 'Психология', null);

INSERT INTO publisher (id, name) VALUES (8, 'Весь');
INSERT INTO publisher (id, name) VALUES (9, 'Эксмо');
INSERT INTO publisher (id, name) VALUES (10, 'Литер');
INSERT INTO publisher (id, name) VALUES (11, 'Азбука');

INSERT INTO author (fio, birthday, views) VALUES ('Борис Акунин', '1956-02-04', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Пауло Коэльо', '1947-02-11', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Дарья Донцова', '1952-06-07', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Джоан Роулинг', '1965-07-31', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Чайлд Ли', '1954-10-29', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Татьяна Устинова', '1968-04-21', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Эрих Мария Ремарк', '1898-06-22', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Владимир Набоков', '1899-04-22', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Памела Трэверс', '1899-08-09', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Братья Гримм', '1786-02-03', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Михаил Юрьевич Лермонтов', '1814-10-15', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Пушкин Александр Сергеевич', '1799-06-06', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Александр Дюма', '1863-06-25', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Теофиль Готье', '1865-03-20', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Дэвид Вайз', '1956-03-01', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Джефри Янг', '1962-04-02', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Чарльз Диккенс', '1867-03-06', 0);
INSERT INTO author (fio, birthday, views) VALUES ('Николай Лесков', '1895-04-04', 0);