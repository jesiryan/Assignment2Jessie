CREATE TABLE `Member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `dateJoined` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- You can use this file to load seed data into the database using SQL statements
insert into Member (id, name, password, dateJoined) values (0, 'Adana', 'toffee', '2013-01-01 00:00');
insert into Member (id, name, password, dateJoined) values (1, 'Ciara', 'toffee', '2013-01-01 00:00');
insert into Member (id, name, password, dateJoined) values (2, 'Jessie', 'toffee', '2013-01-01 00:00');
insert into Member (id, name, password, dateJoined) values (3, 'Rebecca', 'toffee', '2013-01-01 00:00');


CREATE TABLE `Todo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

insert into Todo (id, name, description) values (1, 'Adana', 'study');
insert into Todo (id, name, description) values (2, 'Ciara', 'play music');
insert into Todo (id, name, description) values (3, 'Jessie', 'walk dogs');
insert into Todo (id, name, description) values (4, 'Rebecca', 'do something');