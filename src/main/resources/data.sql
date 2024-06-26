CREATE SCHEMA IF NOT EXISTS Eksamen_DB;

INSERT INTO discipline (name, resultType)
VALUES ('100m Løb', 'Time'),
       ('Højdespring', 'Points'),
       ('Diskoskast', 'Distance');


INSERT INTO participant (name, gender, age, club)
VALUES ('Anders Andersen', 'MALE', 25, 'Aalborg Atletikklub'),
       ('Bente Bentsen', 'FEMALE', 30, 'Brøndby Atletikklub'),
       ('Carsten Carstensen', 'MALE', 35, 'Copenhagen Atletikklub'),
       ('Dorthe Dorthesen', 'FEMALE', 40, 'Djursland Atletikklub'),
       ('Erik Eriksen', 'MALE', 45, 'Esbjerg Atletikklub'),
       ('Fie Fiesdatter', 'FEMALE', 50, 'Fredericia Atletikklub');

INSERT INTO participant_discipline (participant_id, discipline_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 1),
       (1, 2),
       (5, 2),
       (6, 2),
       (2, 3),
       (3, 3),
       (4, 3),
       (5, 3),
       (6, 3);

INSERT INTO result (participant_id, discipline_id, date, resultType, resultValue)
VALUES (1, 1, '2022-01-01 10:00:00', 'Time', '10'),
       (2, 2, '2022-01-02 11:00:00', 'Points', '20'),
       (3, 3, '2022-01-03 12:00:00', 'Distance', '30'),
       (4, 1, '2022-01-04 13:00:00', 'Time', '40'),
       (5, 2, '2022-01-05 14:00:00', 'Points', '50'),
       (6, 3, '2022-01-06 15:00:00', 'Distance', '60');