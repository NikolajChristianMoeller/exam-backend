CREATE SCHEMA IF NOT EXISTS Eksamen_DB;

CREATE TABLE IF NOT EXISTS participant (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           name VARCHAR(255) NOT NULL,
                                           gender ENUM('Male', 'Female', 'Other') NOT NULL,
                                           age INT NOT NULL,
                                           club VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS discipline (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL,
                                          resultType ENUM('Time', 'Height', 'Distance') NOT NULL
);

CREATE TABLE IF NOT EXISTS result (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      resultType ENUM('Time', 'Height', 'Distance') NOT NULL,
                                      date DATE NOT NULL,
                                      resultValue DECIMAL(10,2) NOT NULL,
                                      participantId INT NOT NULL,
                                      disciplineId INT NOT NULL,
                                      FOREIGN KEY (participantId) REFERENCES participant(id),
                                      FOREIGN KEY (disciplineId) REFERENCES discipline(id)
);
