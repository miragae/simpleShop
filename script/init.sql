CREATE DATABASE IF NOT EXISTS `shopdb`;
USE `shopdb`;

CREATE TABLE IF NOT EXISTS `Category` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Category` (`id`, `name`, `description`) VALUES
	(1, 'CPU', 'CPU is the abbreviation for Central Processing Unit (the processor). The CPU is the brains of the computer where most calculations take place.'),
	(2, 'GPU', 'Your graphics card is the most important component of any gaming PC. Make sure you get the right one for your next rig.'),
	(3, 'RAM', 'The more RAM in a computer, the more capacity the computer has to hold and process large programs and files.'),
	(4, 'PSU', 'The Power Supply Unit is the piece of hardware that\'s used to convert the power provided from the outlet into usable power for the many parts inside the computer case.'),
	(5, 'HDD', 'The Hard Drive Disc is used as permanent storage for data.');



CREATE TABLE IF NOT EXISTS `Product` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `price` decimal(14,2) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Category` (`category_id`),
  CONSTRAINT `FK_Category` FOREIGN KEY (`category_id`) REFERENCES `Category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `Product` (`id`, `name`, `category_id`, `price`, `currency`) VALUES
	(6, 'Intel Core i7 7700K',  1, 1545.00, 'PLN'),
	(7, 'Intel Core i7 6950X',  1, 2150.00,  'USD'),
	(8, 'Intel Core i7 6900K',  1, 1140.00,  'EUR'),
	(9, 'Intel Core i7 6700T', 1, 1600.00, 'PLN'),
	(10, 'AMD Ryzen 7 1800X',   1, 2200.00, 'PLN'),
	(11, 'AMD Ryzen 7 1700X',  1, 1735.00, 'PLN'),
	(12, 'AMD Ryzen 5 1600X',  1, 313.00,  'USD'),
	(13, 'Nvidia GTX 1050Ti',  2, 158.00,  'EUR'),
	(14, 'Nvidia GTX 1060', 2, 1130.00, 'PLN'),
	(15, 'Nvidia GTX 1070',   2, 464.66, 'EUR'),
	(16, 'Nvidia GTX 1080Ti',  2, 3200.00, 'PLN'),
	(17, 'Radeon RX 480',  2, 322.78,  'USD'),
	(18, 'Radeon R9 380',  2, 250.29,  'EUR'),
	(19, 'Radeon RX 580', 2, 508.38, 'USD'),
	(21, 'Kingston HyperX FURY 16GB',   3, 550.00, 'PLN'),
	(22, 'GoodRam Play DDR3 4GB',  3, 29.90, 'EUR'),
	(23, 'HyperX Savage 2x4GB',  3, 87.00,  'USD'),
	(24, 'Corsair Vengeance 2x4GB',  3, 76.63,  'EUR'),
	(25, 'SilentiumPC Vero L1 500W', 4, 175.00, 'PLN'),
	(26, 'Corsair VS 550W',   4, 205.00, 'PLN'),
	(27, 'Corsair RM 750X 750W',  4, 116.15, 'EUR'),
	(28, 'Corsair HX 1000i 1000W',  4, 260.88,  'USD'),
	(29, 'Western Digital Caviar Blue 1TB',  5, 55.00,  'EUR'),
	(30, 'Seagate BarraCuda 1TB', 5, 59.17, 'USD'),
	(31, 'Toshiba 3TB',   5, 350.00, 'PLN'),
	(32, 'HGST Ultrastar 7K6000 6TB',  5, 332.87, 'EUR');


CREATE TABLE IF NOT EXISTS `Hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Hibernate_sequence` (`next_val`) VALUES
	(33);
