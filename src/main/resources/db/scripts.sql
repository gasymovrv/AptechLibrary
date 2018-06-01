SELECT * FROM book b where b.genre_id in (select g.id from genre g where g.name like '%ю%');

SELECT b.name, g.name FROM book b
join genre g on b.genre_id=g.id;

SELECT b.name, a.fio, g.name, p.name FROM book b
join author a on b.author_id=a.id
join genre g on b.genre_id=g.id
join publisher p on b.publisher_id=p.id;
INSERT INTO `user_role`(`username`, `role`) VALUES ('admin', 'ROLE_USER');