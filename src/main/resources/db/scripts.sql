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