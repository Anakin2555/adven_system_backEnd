USE registersystem;

DROP TABLE IF EXISTS USER;
CREATE TABLE USER(
	uid BIGINT(50),
	`password` VARCHAR(50) NOT NULL,
	identity TINYINT NOT NULL DEFAULT 0,
	`name` VARCHAR(50) NOT NULL,
	grade INT DEFAULT 0,
	is_delete TINYINT DEFAULT 0, 
	PRIMARY KEY(uid)
);



DROP TABLE IF EXISTS EVENT;
CREATE TABLE EVENT(
	eid BIGINT AUTO_INCREMENT,
	theme VARCHAR(50) NOT NULL,
	info VARCHAR(300),
	startdate DATETIME NOT NULL,
	enddate DATETIME NOT NULL,
	`type` VARCHAR(30) NOT NULL,
	studentNumber INT,
	teacherNumber INT,
	is_delete TINYINT DEFAULT 0, 
	PRIMARY KEY(eid)
);

DROP TABLE IF EXISTS apply;
CREATE TABLE apply(
	eid BIGINT,
	uid BIGINT,
	`status` TINYINT NOT NULL DEFAULT 0,
	is_delete TINYINT DEFAULT 0,                                                                                                                                                                                         
	PRIMARY KEY(eid,uid),
	FOREIGN KEY(uid) REFERENCES USER(uid),
	FOREIGN KEY(eid) REFERENCES EVENT(eid)
);


DROP TABLE IF EXISTS feedback;
CREATE TABLE feedback(
	eid BIGINT,
	uid BIGINT,
	`status` TINYINT NOT NULL DEFAULT 0,
	is_delete TINYINT DEFAULT 0,   
	image MEDIUMBLOB,                                                                                                                                                                                
	PRIMARY KEY(eid,uid),
	FOREIGN KEY(uid) REFERENCES USER(uid),
	FOREIGN KEY(eid) REFERENCES EVENT(eid)
)








