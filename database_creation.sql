DROP DATABASE IF EXISTS `films_webapp_december_2022`;
CREATE DATABASE `films_webapp_december_2022`;
USE `films_webapp_december_2022` ;


DROP TABLE IF EXISTS `films_webapp_december_2022`.`comment`;
DROP TABLE IF EXISTS `films_webapp_december_2022`.`watched_film`;
DROP TABLE IF EXISTS `films_webapp_december_2022`.`starred_film`;
DROP TABLE IF EXISTS `films_webapp_december_2022`.`film`;
DROP TABLE IF EXISTS `films_webapp_december_2022`.`user`;


-- -----------------------------------------------------
-- Table `films_webapp_december_2022`.`film`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `films_webapp_december_2022`.`film` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `year` INT NOT NULL,
  `image_name` VARCHAR(50) NOT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `status` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `films_webapp_december_2022`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `films_webapp_december_2022`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  `password` VARCHAR(130) NOT NULL,
  `status` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `films_webapp_december_2022`.`starred_film`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `films_webapp_december_2022`.`starred_film` (
  `film_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  INDEX `fk_starred_film_film1_idx` (`film_id` ASC) VISIBLE,
  INDEX `fk_starred_film_user1_idx` (`user_id` ASC) VISIBLE,
  PRIMARY KEY (`film_id`, `user_id`),
  CONSTRAINT `fk_starred_film_film1`
    FOREIGN KEY (`film_id`)
    REFERENCES `films_webapp_december_2022`.`film` (`id`),
  CONSTRAINT `fk_starred_film_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `films_webapp_december_2022`.`user` (`id`));


-- -----------------------------------------------------
-- Table `films_webapp_december_2022`.`watched_film`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `films_webapp_december_2022`.`watched_film` (
  `film_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  INDEX `fk_watched_film_film_idx` (`film_id` ASC) VISIBLE,
  INDEX `fk_watched_film_user1_idx` (`user_id` ASC) VISIBLE,
  PRIMARY KEY (`film_id`, `user_id`),
  CONSTRAINT `fk_watched_film_film`
    FOREIGN KEY (`film_id`)
    REFERENCES `films_webapp_december_2022`.`film` (`id`),
  CONSTRAINT `fk_watched_film_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `films_webapp_december_2022`.`user` (`id`));


-- -----------------------------------------------------
-- Table `films_webapp_december_2022`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `films_webapp_december_2022`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `comment` TEXT NOT NULL,
  `date` DATETIME NOT NULL,
  `status` VARCHAR(40) NOT NULL,
  `film_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_film1_idx` (`film_id` ASC) VISIBLE,
  INDEX `fk_comment_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_film1`
    FOREIGN KEY (`film_id`)
    REFERENCES `films_webapp_december_2022`.`film` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `films_webapp_december_2022`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    

insert into user (name, surname, email, role, password, status) values ("u1", "u1", "u1@email.com", "ROLE_USER", "$2a$12$zxv4PYNx.sBat7qdpz0RhO3tQh0sAnTJC6iBosGYD5FIxdqPWo5Qu", "ACTIVE");
insert into user (name, surname, email, role, password, status) values ("u2", "u2", "u2@email.com", "ROLE_USER", "$2a$12$ism8IHpbKL71w0x1YoOb2O7uyPShncXNXCXUTaISln9iLb2BWrWTu", "ACTIVE");
insert into user (name, surname, email, role, password, status) values ("admin", "admin", "admin@email.com", "ROLE_ADMIN", "$2a$12$y6kB1ogEa04B7hb8m.MUqOOMC5vQ0xgizvcCIG9GYO3BhWZ.bPqL2", "ACTIVE");
