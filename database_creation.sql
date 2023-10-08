DROP TABLE IF EXISTS "comment";
DROP TABLE IF EXISTS "watched_film";
DROP TABLE IF EXISTS "starred_film";
DROP TABLE IF EXISTS "film";
DROP TABLE IF EXISTS "user";


-- -----------------------------------------------------
-- Table "film"
-- -----------------------------------------------------
CREATE TABLE "film" (
                        "id" SERIAL PRIMARY KEY NOT NULL,
                        "name" VARCHAR(50) NOT NULL,
                        "year" INT NOT NULL,
                        "image_name" VARCHAR(50) NOT NULL,
                        "description" TEXT,
                        "status" VARCHAR(40) NOT NULL
);


-- -----------------------------------------------------
-- Table "user"
-- -----------------------------------------------------
CREATE TABLE "user" (
                        "id" SERIAL PRIMARY KEY NOT NULL,
                        "name" VARCHAR(45) NOT NULL,
                        "surname" VARCHAR(45) NOT NULL,
                        "email" VARCHAR(70) NOT NULL UNIQUE,
                        "role" VARCHAR(45) NOT NULL,
                        "password" VARCHAR(130) NOT NULL,
                        "status" VARCHAR(40) NOT NULL
);


-- -----------------------------------------------------
-- Table "starred_film"
-- -----------------------------------------------------
CREATE TABLE "starred_film" (
                                "film_id" INT NOT NULL REFERENCES "film"("id"),
                                "user_id" INT NOT NULL REFERENCES "user"("id"),
                                CONSTRAINT "starred_film_pkey" PRIMARY KEY ("film_id", "user_id")
);


-- -----------------------------------------------------
-- Table "watched_film"
-- -----------------------------------------------------
CREATE TABLE "watched_film" (
                                "film_id" INT NOT NULL REFERENCES "film"("id"),
                                "user_id" INT NOT NULL REFERENCES "user"("id"),
                                CONSTRAINT "watched_film_pkey" PRIMARY KEY ("film_id", "user_id")
);


-- -----------------------------------------------------
-- Table "comment"
-- -----------------------------------------------------
CREATE TABLE "comment"(
                          "id" SERIAL PRIMARY KEY NOT NULL ,
                          "comment" TEXT NOT NULL,
                          "date" TIMESTAMP NOT NULL,
                          "status" VARCHAR(40) NOT NULL,
                          "film_id" INT NOT NULL REFERENCES "film"("id"),
                          "user_id" INT NOT NULL REFERENCES "user"("id")
);


