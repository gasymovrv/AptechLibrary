SELECT * FROM book b where b.genre_id in (select g.id from genre g where g.name like '%ю%');

SELECT b.name, g.name FROM book b
join genre g on b.genre_id=g.id;

SELECT b.name, a.fio, g.name, p.name FROM book b
join author a on b.author_id=a.id
join genre g on b.genre_id=g.id
join publisher p on b.publisher_id=p.id;

CREATE  TABLE user (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(64) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));

CREATE TABLE user_role (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES user (username));

CREATE TABLE persistent_logins (
  username varchar(64) not null,
  series varchar(64) not null,
  token varchar(64) not null,
  last_used timestamp not null,
  PRIMARY KEY (series)
);

INSERT INTO `user`(`username`, `password`, `enabled`) VALUES ('admin', '$2a$05$IHUj33qfJo7veMhOY06CxOEt6Y6IPZX5cB3zbxuz.EVdUvd.XNrou', true);
INSERT INTO `user`(`username`, `password`, `enabled`) VALUES ('user', '$2a$05$IHUj33qfJo7veMhOY06CxOEt6Y6IPZX5cB3zbxuz.EVdUvd.XNrou', true);
INSERT INTO `user_role`(`username`, `role`) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO `user_role`(`username`, `role`) VALUES ('user', 'ROLE_USER');


