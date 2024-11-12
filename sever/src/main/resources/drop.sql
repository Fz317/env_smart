DROP PROCEDURE IF EXISTS drop_tables;
DELIMITER //
CREATE PROCEDURE drop_tables()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE table_name VARCHAR(20);
    WHILE i <= 31 DO
            SET table_name = CONCAT('e_detail_', i);
            SET @sql = CONCAT('drop TABLE IF EXISTS ',table_name);
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
            SET i = i + 1;
        END WHILE;
END //
DELIMITER ;
call drop_tables();