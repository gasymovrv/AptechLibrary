-- ------------------------------------------ STRUCTURE -----------------------------------------

DROP TABLE IF EXISTS "author";
CREATE TABLE author (
  "id" SERIAL,
  "fio" varchar(300) NOT NULL,
  "birthday" date DEFAULT NULL,
  "views" bigint NOT NULL DEFAULT '0',
  "created" timestamp(0) DEFAULT NULL,
  PRIMARY KEY ("id")
);


DROP TABLE IF EXISTS "genre";
CREATE TABLE "genre" (
  "id" SERIAL,
  "name" varchar(100) NOT NULL,
  "parent" bigint DEFAULT NULL,
  PRIMARY KEY ("id")
  ,
  CONSTRAINT "fk_parent" FOREIGN KEY ("parent") REFERENCES "genre" ("id")
);
CREATE INDEX "fk_parent_idx" ON "genre" ("parent");


DROP TABLE IF EXISTS "publisher";
CREATE TABLE "publisher" (
  "id" SERIAL,
  "name" varchar(100) NOT NULL,
  PRIMARY KEY ("id")
);


DROP TABLE IF EXISTS "book";
CREATE TABLE "book" (
  "id" SERIAL,
  "name" varchar(450) NOT NULL,
  "page_count" int NOT NULL,
  "isbn" varchar(100) NOT NULL,
  "genre_id" bigint NOT NULL,
  "author_id" bigint NOT NULL,
  "publish_year" int NOT NULL,
  "publisher_id" bigint NOT NULL,
  "image" bytea,
  "descr" varchar(5000) DEFAULT NULL,
  "bookcol" varchar(45) DEFAULT NULL,
  "rating" int DEFAULT 0,
  "vote_count" bigint DEFAULT 0,
  "views" bigint NOT NULL DEFAULT 0,
  "created" timestamp(0) DEFAULT NULL,
  "price" decimal(16,2) NOT NULL,
  "file_extension" varchar(20) NOT NULL DEFAULT 'pdf',
  "content_type" varchar(450) DEFAULT 'application/pdf',
  "file_size" varchar(45) DEFAULT NULL,
  PRIMARY KEY ("id"),
  CONSTRAINT "isbn_UNIQUE" UNIQUE  ("isbn")
  ,
  CONSTRAINT "fk_author" FOREIGN KEY ("author_id") REFERENCES "author" ("id") ON UPDATE CASCADE,
  CONSTRAINT "fk_genre" FOREIGN KEY ("genre_id") REFERENCES "genre" ("id") ON UPDATE CASCADE,
  CONSTRAINT "fk_publisher" FOREIGN KEY ("publisher_id") REFERENCES "publisher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
);
CREATE INDEX "fk_author_idx" ON "book" ("author_id");
CREATE INDEX "fk_genre_idx" ON "book" ("genre_id");
CREATE INDEX "fk_publiher_idx" ON "book" ("publisher_id");


DROP TABLE IF EXISTS "book_content";
CREATE TABLE "book_content" (
  "id" SERIAL,
  "book_id" bigint NOT NULL,
  "content" bytea NOT NULL,
  PRIMARY KEY ("id")
  ,
  CONSTRAINT "fk_book_content" FOREIGN KEY ("book_id") REFERENCES "book" ("id") ON DELETE CASCADE
);
CREATE INDEX "fk_book_content" ON "book_content" ("book_id");


DROP TABLE IF EXISTS "user";
CREATE TABLE "user" (
  "username" varchar(45) NOT NULL,
  "password" varchar(64) NOT NULL,
  "enabled" boolean NOT NULL DEFAULT TRUE,
  "money" decimal(16,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY ("username")
);


DROP TABLE IF EXISTS "cart";
CREATE TABLE "cart" (
  "username" varchar(45) NOT NULL,
  PRIMARY KEY ("username"),
  CONSTRAINT "fk_cart_to_user" FOREIGN KEY ("username") REFERENCES "user" ("username")
);


DROP TABLE IF EXISTS "carts_to_books";
CREATE TABLE "carts_to_books" (
  "username" varchar(45) NOT NULL,
  "book_id" bigint NOT NULL,
  PRIMARY KEY ("username","book_id")
  ,
  CONSTRAINT "fk_c_to_book" FOREIGN KEY ("book_id") REFERENCES "book" ("id"),
  CONSTRAINT "fk_cart" FOREIGN KEY ("username") REFERENCES "cart" ("username")
);
CREATE INDEX "fk_c_to_book" ON "carts_to_books" ("book_id");


DROP TABLE IF EXISTS "order_";
CREATE TABLE "order_" (
  "id" SERIAL,
  "username" varchar(45) NOT NULL,
  "created" timestamp(0) DEFAULT NULL,
  PRIMARY KEY ("id")
  ,
  CONSTRAINT "fk_order_to_user" FOREIGN KEY ("username") REFERENCES "user" ("username")
);
CREATE INDEX "fk_order_to_user" ON "order_" ("username");


DROP TABLE IF EXISTS "orders_to_books";
CREATE TABLE "orders_to_books" (
  "order_id" bigint NOT NULL,
  "book_id" bigint NOT NULL,
  PRIMARY KEY ("order_id","book_id")
  ,
  CONSTRAINT "fk_o_to_book" FOREIGN KEY ("book_id") REFERENCES "book" ("id"),
  CONSTRAINT "fk_order" FOREIGN KEY ("order_id") REFERENCES "order_" ("id")
);
CREATE INDEX "fk_o_to_book" ON "orders_to_books" ("book_id");


DROP TABLE IF EXISTS "persistent_logins";
CREATE TABLE "persistent_logins" (
  "username" varchar(64) NOT NULL,
  "series" varchar(64) NOT NULL,
  "token" varchar(64) NOT NULL,
  "last_used" timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY ("series")
);


DROP TABLE IF EXISTS "user_role";
CREATE TABLE "user_role" (
  "user_role_id" SERIAL,
  "username" varchar(45) NOT NULL,
  "role" varchar(45) NOT NULL,
  PRIMARY KEY ("user_role_id"),
  CONSTRAINT "uni_username_role" UNIQUE  ("role","username")
  ,
  CONSTRAINT "fk_username" FOREIGN KEY ("username") REFERENCES "user" ("username")
);
CREATE INDEX "fk_username_idx" ON "user_role" ("username");



DROP TABLE IF EXISTS "users_views";
CREATE TABLE "users_views" (
  "id" SERIAL,
  "username" varchar(45) NOT NULL,
  "book_id" bigint NOT NULL,
  "views" bigint NOT NULL DEFAULT '0',
  PRIMARY KEY ("id"),
  CONSTRAINT "user_book_unq" UNIQUE  ("username","book_id")
  ,
  CONSTRAINT "fk_book" FOREIGN KEY ("book_id") REFERENCES "book" ("id"),
  CONSTRAINT "fk_user" FOREIGN KEY ("username") REFERENCES "user" ("username")
);
CREATE INDEX "fk_book" ON "users_views" ("book_id");


DROP TABLE IF EXISTS "vote";
CREATE TABLE "vote" (
  "id" SERIAL,
  "value" int DEFAULT '0',
  "book_id" bigint NOT NULL,
  "username" varchar(100) NOT NULL,
  PRIMARY KEY ("id"),
  CONSTRAINT "fk_book_id" FOREIGN KEY ("book_id") REFERENCES "book" ("id") ON DELETE CASCADE ON UPDATE NO ACTION
);
CREATE INDEX "fk_book_id_idx" ON "vote" ("book_id");
CREATE INDEX "fk_user_id_idx" ON "vote" ("username");

-- Таблица для приема данных от тестовых XSS-инъекций
DROP TABLE IF EXISTS "xss_data";
CREATE TABLE "xss_data" (
  "id" SERIAL,
  "location" varchar(1000) NULL,
  "data" varchar(10000) NULL,
  "created" timestamp(0) DEFAULT current_timestamp,
  PRIMARY KEY ("id")
);


-- --------------------------------------- DATA ----------------------------------------------------
-- username:admin password:q
INSERT INTO "user"(username, password, enabled) VALUES ('admin', '$2a$05$IHUj33qfJo7veMhOY06CxOEt6Y6IPZX5cB3zbxuz.EVdUvd.XNrou', TRUE );
-- username:user password:q
INSERT INTO "user"(username, password, enabled) VALUES ('user', '$2a$05$IHUj33qfJo7veMhOY06CxOEt6Y6IPZX5cB3zbxuz.EVdUvd.XNrou', TRUE );

INSERT INTO user_role(username, role) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_role(username, role) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_role(username, role) VALUES ('user', 'ROLE_USER');

INSERT INTO cart(username) VALUES ('user');
INSERT INTO cart(username) VALUES ('admin');

INSERT INTO genre (name, parent) VALUES ('Детектив', null);
INSERT INTO genre (name, parent) VALUES ('Мистика', null);
INSERT INTO genre (name, parent) VALUES ('Юмор', null);
INSERT INTO genre (name, parent) VALUES ('Исторический', null);
INSERT INTO genre (name, parent) VALUES ('Роман', null);
INSERT INTO genre (name, parent) VALUES ('Сказка', null);
INSERT INTO genre (name, parent) VALUES ('Приключения', null);
INSERT INTO genre (name, parent) VALUES ('Бизнес', null);
INSERT INTO genre (name, parent) VALUES ('Боевик', null);
INSERT INTO genre (name, parent) VALUES ('Религия', null);
INSERT INTO genre (name, parent) VALUES ('Компьютеры', null);
INSERT INTO genre (name, parent) VALUES ('Семья', null);
INSERT INTO genre (name, parent) VALUES ('Психология', null);

INSERT INTO publisher (name) VALUES ('Весь');
INSERT INTO publisher (name) VALUES ('Эксмо');
INSERT INTO publisher (name) VALUES ('Литер');
INSERT INTO publisher (name) VALUES ('Азбука');

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