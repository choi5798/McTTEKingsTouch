CREATE DATABASE order_mgmt;

USE order_mgmt;

CREATE TABLE `burgers`
(
    `burger_id`      binary(16) PRIMARY KEY,
    `burger_name`    varchar(30) NOT NULL UNIQUE,
    `price`          bigint      NOT NULL,
    `burger_type`    varchar(30) NOT NULL,
    `burger_company` varchar(10) NOT NULL,
    `created_at`     datetime(6) NOT NULL,
    `updated_at`     datetime(6) DEFAULT null
);

CREATE TABLE `orders`
(
    `order_id`   binary(16) PRIMARY KEY,
    `email`      varchar(100) NOT NULL,
    `address`    varchar(200) NOT NULL,
    `postcode`   varchar(50)  NOT NULL,
    `status`     varchar(20)  NOT NULL,
    `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) DEFAULT null
);

CREATE TABLE `order_items`
(
    `seq`         bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `burger_id`   binary(16),
    `order_id`    binary(16),
    `burger_name` varchar(30),
    `quantity`    int                NOT NULL,
    `created_at`  datetime(6) NOT NULL,
    `updated_at`  datetime(6) DEFAULT null,
    INDEX(`order_id`)
);

ALTER TABLE `order_items`
    ADD FOREIGN KEY (`burger_id`) REFERENCES burgers (`burger_id`) ON DELETE CASCADE;

ALTER TABLE `order_items`
    ADD FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE;