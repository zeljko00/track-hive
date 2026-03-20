CREATE USER IF NOT EXISTS 'track-hive-backend'@'%' IDENTIFIED BY 'secret';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, INDEX, DROP ON `track-hive`.* TO 'track-hive-backend'@'%';
FLUSH PRIVILEGES;