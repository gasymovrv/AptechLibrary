# Здесь разные тестовые скрипты

SELECT * FROM book b where b.genre_id in (select g.id from genre g where g.name like '%ю%');

SELECT b.name, g.name FROM book b
join genre g on b.genre_id=g.id;

SELECT b.name, a.fio, g.name, p.name FROM book b
join author a on b.author_id=a.id
join genre g on b.genre_id=g.id
join publisher p on b.publisher_id=p.id;
INSERT INTO `user_role`(`username`, `role`) VALUES ('admin', 'ROLE_USER');

UPDATE author a
SET a.views = ifnull((SELECT sum(b.views)
                      FROM book b
                      WHERE b.author_id = a.id), 0);

ALTER TABLE author ADD COLUMN created DATETIME NOT NULL DEFAULT 0;

UPDATE author SET created = current_date();

CREATE TABLE users_views (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  book_id bigint(20) NOT NULL,
  CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book (id) ON UPDATE CASCADE,
  PRIMARY KEY (id)
);

INSERT INTO users_views(username, book_id) VALUES ('user', 6);


CREATE TABLE users_views (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  book_id bigint(20) NOT NULL,
  views bigint(20) NOT NULL DEFAULT '0',
  UNIQUE KEY user_book_unq (username, book_id),
  CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book (id),
  CONSTRAINT fk_user FOREIGN KEY (username) REFERENCES user (username),
  PRIMARY KEY (id)
);

ALTER TABLE book ADD COLUMN price DECIMAL(16, 2) NOT NULL;
UPDATE book SET price = (rand()*10000)+300;