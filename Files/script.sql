DROP DATABASE IF EXISTS SelfieUserDatabase;
CREATE DATABASE SelfieUserDatabase;

USE SelfieUserDatabase;

CREATE TABLE User (
    username VARCHAR(30) NOT NULL PRIMARY KEY,
    password VARCHAR(30) NOT NULL,
    fname VARCHAR(30) NOT NULL,
    lname VARCHAR(30) NOT NULL,
    numberOfImages INT(11),
    numberOfFriends INT(11)
);

CREATE TABLE ImageURL (
	imageNum INT(11) PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(30) NOT NULL,
    bImage mediumblob NOT NULL,
    score VARCHAR(45) NOT NULL,
    userImageNum INT(11) NOT NULL
);

CREATE TABLE FriendList(
	friendNum INT(11) PRIMARY KEY AUTO_INCREMENT,
    username1 VARCHAR(30) NOT NULL,
    username2 VARCHAR(30) NOT NULL
);

CREATE TABLE FriendRequests(
	requestNum INT(11) PRIMARY KEY AUTO_INCREMENT,
    username1 VARCHAR(30) NOT NULL,
    username2 VARCHAR(30) NOT NULL
);
            
INSERT INTO User (username, password, fname, lname, numberOfImages, numberOfFriends)
	VALUES	('hrishi', '12345', 'hrishi', 'vora', 0, 1),
			('sam', '12345', 'sam', 'kushell', 0, 2),
			('steven', '12345', 'steven', 'zhao', 0, 1);
            
INSERT INTO FriendList(username1, username2) 
	VAlUES ('hrishi', 'sam'),
    ('sam', 'steven'),
    ('sam', 'hrishi'),
    ('steven', 'sam');
    
    
SELECT * FROM FriendList WHERE username1='steven'