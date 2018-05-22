SELECT * FROM book b where b.genre_id in (select g.id from genre g where g.name like '%ю%');

SELECT b.name, g.name FROM book b
join genre g on b.genre_id=g.id;

SELECT b.name, a.fio, g.name, p.name FROM book b
join author a on b.author_id=a.id
join genre g on b.genre_id=g.id
join publisher p on b.publisher_id=p.id;

INSERT INTO `user`(`username`, `password`, `enabled`) VALUES ('admin', '$2a$05$IHUj33qfJo7veMhOY06CxOEt6Y6IPZX5cB3zbxuz.EVdUvd.XNrou', true);
INSERT INTO `user`(`username`, `password`, `enabled`) VALUES ('user', '$2a$05$IHUj33qfJo7veMhOY06CxOEt6Y6IPZX5cB3zbxuz.EVdUvd.XNrou', true);
INSERT INTO `user_role`(`username`, `role`) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO `user_role`(`username`, `role`) VALUES ('user', 'ROLE_USER');