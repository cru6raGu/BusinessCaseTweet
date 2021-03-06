-- MySQL Script generated by MySQL Workbench
-- Fri 08 Sep 2017 10:19:38 AM PDT
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema tweet_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `tweet_db` ;

-- -----------------------------------------------------
-- Schema tweet_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tweet_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `tweet_db` ;

-- -----------------------------------------------------
-- Table `tweet_db`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tweet_db`.`user` ;

CREATE TABLE IF NOT EXISTS `tweet_db`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `guid` VARCHAR(45) NOT NULL,
  `username` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(1024) NOT NULL,
  `last_name` VARCHAR(1024) NOT NULL,
  `middle_name` VARCHAR(45) NULL,
  `status` VARCHAR(45) NOT NULL,
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `guid_UNIQUE` (`guid` ASC),
  PRIMARY KEY (`id`),
  INDEX `index_id` (`id` ASC),
  INDEX `index_guid` (`guid` ASC),
  INDEX `index_status` (`status` ASC));


-- -----------------------------------------------------
-- Table `tweet_db`.`tweet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tweet_db`.`tweet` ;

CREATE TABLE IF NOT EXISTS `tweet_db`.`tweet` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `guid` VARCHAR(45) NOT NULL,
  `tweet` VARCHAR(255) NOT NULL,
  `from_user_id` BIGINT NOT NULL,
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `guid_UNIQUE` (`guid` ASC),
  PRIMARY KEY (`id`),
  INDEX `index_guid` (`guid` ASC),
  INDEX `index_id` (`id` ASC),
  INDEX `fk_tweet_from_user_id_idx` (`from_user_id` ASC),
  INDEX `index7` (`tweet` ASC),
  CONSTRAINT `fk_tweet_from_user_id`
    FOREIGN KEY (`from_user_id`)
    REFERENCES `tweet_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `tweet_db`.`tweet_message_relationship`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tweet_db`.`tweet_message_relationship` ;

CREATE TABLE IF NOT EXISTS `tweet_db`.`tweet_message_relationship` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `tweet_id` BIGINT NOT NULL,
  `to_user_id` BIGINT NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  PRIMARY KEY (`id`),
  INDEX `fk_tweet_relationship_to_user_id_idx` (`to_user_id` ASC),
  INDEX `fk_tweet_relationship_tweet_id_idx` (`tweet_id` ASC),
  CONSTRAINT `fk_tweet_relationship_to_user_id`
    FOREIGN KEY (`to_user_id`)
    REFERENCES `tweet_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tweet_relationship_tweet_id`
    FOREIGN KEY (`tweet_id`)
    REFERENCES `tweet_db`.`tweet` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `tweet_db`.`tweet_follower`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tweet_db`.`tweet_follower` ;

CREATE TABLE IF NOT EXISTS `tweet_db`.`tweet_follower` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `guid` VARCHAR(45) NOT NULL,
  `user_id` BIGINT NOT NULL,
  `follower_user_id` BIGINT NOT NULL,
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `guid_UNIQUE` (`guid` ASC),
  PRIMARY KEY (`id`),
  INDEX `fk_tweet_follower_user_id_idx` (`user_id` ASC),
  INDEX `fk_tweet_follower_follower_user_id_idx` (`follower_user_id` ASC),
  CONSTRAINT `fk_tweet_follower_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `tweet_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tweet_follower_follower_user_id`
    FOREIGN KEY (`follower_user_id`)
    REFERENCES `tweet_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
