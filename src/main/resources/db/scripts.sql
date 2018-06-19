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