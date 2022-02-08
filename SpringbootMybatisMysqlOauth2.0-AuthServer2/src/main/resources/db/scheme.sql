CREATE TABLE `oauth_client` (
	`client_seq` INT(11) NOT NULL AUTO_INCREMENT,
	`client_id` VARCHAR(256) NOT NULL,
	`client_secret` VARCHAR(256) NOT NULL,
	`status` VARCHAR(50) NOT NULL DEFAULT 'A',
	`reg_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`client_seq`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=5
;

CREATE TABLE `oauth_refresh_token` (
	`token_id` INT(11) NOT NULL AUTO_INCREMENT,
	`token` MEDIUMBLOB NULL DEFAULT NULL,
	`authentication` MEDIUMBLOB NULL DEFAULT NULL,
	PRIMARY KEY (`token_id`) USING BTREE
)
COMMENT='리프레시 토큰 테이블'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=15
;


CREATE TABLE `user` (
	`user_seq` INT(11) NOT NULL AUTO_INCREMENT,
	`user_id` VARCHAR(100) NULL DEFAULT NULL,
	`password` VARCHAR(256) NOT NULL,
	`name` VARCHAR(100) NOT NULL,
	`status` VARCHAR(100) NOT NULL DEFAULT 'A',
	`reg_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`user_seq`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=3
;


