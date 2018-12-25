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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
)
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE INDEX "fk_author_idx" ON "book" ("author_id");
CREATE INDEX "fk_genre_idx" ON "book" ("genre_id");
CREATE INDEX "fk_publiher_idx" ON "book" ("publisher_id");

--
-- Table structure for table "book_content"
--

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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
)
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE INDEX "fk_book" ON "users_views" ("book_id");

--
-- Table structure for table "vote"
--

DROP TABLE IF EXISTS "vote";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "vote" (
  "id" SERIAL,
  "value" int DEFAULT '0',
  "book_id" bigint NOT NULL,
  "username" varchar(100) NOT NULL,
  PRIMARY KEY ("id"),
  CONSTRAINT "fk_book_id" FOREIGN KEY ("book_id") REFERENCES "book" ("id") ON DELETE CASCADE ON UPDATE NO ACTION
)
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE INDEX "fk_book_id_idx" ON "vote" ("book_id");
CREATE INDEX "fk_user_id_idx" ON "vote" ("username");
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;