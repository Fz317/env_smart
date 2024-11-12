DELIMITER //
CREATE PROCEDURE create_tables()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE table_name VARCHAR(20);
    WHILE i <= 31 DO
            SET table_name = CONCAT('e_detail_', i);
            SET @sql = CONCAT('CREATE TABLE ', table_name, '(
id INT PRIMARY KEY AUTO_INCREMENT,
name char(20),
srcId char(5),
desId char(5),
devId char(5),
sersorAddress char(7),
count int(2),
cmd char(5),
status int(2),
data float,
gather_date timestamp
)');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
SET i = i + 1;
END WHILE;
END //
DELIMITER ;

call create_tables();