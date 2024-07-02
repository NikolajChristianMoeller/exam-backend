CREATE SCHEMA IF NOT EXISTS eksamen_db;

INSERT INTO participant (full_name, gender, age, adjacent_club, age_group, country)
VALUES
    ('Emma Brown', 'FEMALE', 20, 'Club C', 'JUNIOR', 'DENMARK'),
    ('Oliver Taylor', 'MALE', 35, 'Club D', 'ADULT', 'DENMARK'),
    ('Sophia Wilson', 'FEMALE', 40, 'Club E', 'ADULT', 'DENMARK'),
    ('Mia Thomas', 'FEMALE', 45, 'Club F', 'ADULT', 'DENMARK'),
    ('Harper Jackson', 'FEMALE', 50, 'Club G', 'SENIOR', 'DENMARK'),
    ('Elijah White', 'MALE', 55, 'Club H', 'SENIOR', 'DENMARK'),
    ('James Harris', 'MALE', 60, 'Club I', 'SENIOR', 'DENMARK'),
    ('Benjamin Martin', 'MALE', 65, 'Club J', 'SENIOR', 'DENMARK'),
    ('Lucas Thompson', 'MALE', 70, 'Club K', 'SENIOR', 'DENMARK'),
    ('Mason Garcia', 'MALE', 75, 'Club L', 'SENIOR', 'DENMARK'),
    ('Logan Martinez', 'MALE', 80, 'Club M', 'SENIOR', 'DENMARK'),
    ('Alexander Robinson', 'MALE', 85, 'Club N', 'SENIOR', 'DENMARK'),
    ('Ethan Clark', 'MALE', 90, 'Club O', 'SENIOR', 'DENMARK'),
    ('Henry Rodriguez', 'MALE', 95, 'Club P', 'SENIOR', 'DENMARK'),
    ('Sebastian Lewis', 'MALE', 100, 'Club Q', 'SENIOR', 'DENMARK'),
    ('Jack Lee', 'MALE', 105, 'Club R', 'SENIOR', 'DENMARK'),
    ('Aiden Walker', 'MALE', 110, 'Club S', 'SENIOR', 'DENMARK'),
    ('Theodore Hall', 'MALE', 115, 'Club T', 'SENIOR', 'DENMARK'),
    ('Joseph Young', 'MALE', 120, 'Club U', 'SENIOR', 'DENMARK'),
    ('Samuel Allen', 'MALE', 125, 'Club V', 'SENIOR', 'DENMARK');


INSERT INTO discipline (name, description, results_type)
VALUES
    ('100m Sprint', 'A short distance running event in track and field competitions', 'TIME'),
    ('Long Jump', 'Athletes combine speed, strength, and agility in an attempt to leap as far as possible from a take off point', 'DISTANCE'),
    ('Shot Put', 'Athletes throw a heavy spherical object as far as possible', 'DISTANCE'),
    ('400m Hurdles', 'A track and field hurdling event', 'TIME'),
    ('Discus Throw', 'Athletes throw a heavy disc—called a discus—in an attempt to mark a farther distance than their competitors', 'DISTANCE'),
    ('Javelin Throw', 'Athletes throw a spear-like object made of metal, fibreglass, or carbon fibre', 'DISTANCE'),
    ('1500m', 'A middle-distance track event', 'TIME'),
    ('Decathlon', 'An athletic event taking place over two days, in which each competitor takes part in the same prescribed ten events', 'POINTS');


INSERT INTO participant_disciplines (participant_id, disciplines_id)
VALUES
    (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8),
    (2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6), (2, 7), (2, 8),
    (3, 1),
    (4, 2),
    (5, 3),
    (6, 4),
    (7, 5),
    (8, 6),
    (9, 7),
    (10, 8),
    (11, 1),
    (12, 2),
    (13, 3),
    (14, 4),
    (15, 5),
    (16, 6),
    (17, 7),
    (18, 8),
    (19, 1),
    (20, 2);

INSERT INTO result (result_date, hours, minutes, seconds, hundredths, meters, centimeters, point_value, participant_id, discipline_id)
VALUES
    ('2023-06-01', 0, 10, 25, 50, NULL, NULL, NULL, 1, 1),
    ('2023-06-01', NULL, NULL, NULL, NULL, 6, 75, NULL, 1, 2),
    ('2023-06-01', NULL, NULL, NULL, NULL, 14, 20, NULL, 1, 3),
    ('2023-06-01', 0, 50, 12, 30, NULL, NULL, NULL, 1, 4),
    ('2023-06-01', NULL, NULL, NULL, NULL, 45, 80, NULL, 1, 5),
    ('2023-06-01', NULL, NULL, NULL, NULL, 60, 90, NULL, 1, 6),
    ('2023-06-01', 4, 10, 5, 20, NULL, NULL, NULL, 1, 7),
    ('2023-06-01', NULL, NULL, NULL, NULL, NULL, NULL, 8500, 1, 8),
    ('2023-06-01', 0, 9, 55, 75, NULL, NULL, NULL, 2, 1),
    ('2023-06-01', NULL, NULL, NULL, NULL, 7, 10, NULL, 2, 2),
    ('2023-06-01', NULL, NULL, NULL, NULL, 15, 40, NULL, 2, 3),
    ('2023-06-01', 0, 48, 10, 60, NULL, NULL, NULL, 2, 4),
    ('2023-06-01', NULL, NULL, NULL, NULL, 46, 30, NULL, 2, 5),
    ('2023-06-01', NULL, NULL, NULL, NULL, 61, 50, NULL, 2, 6),
    ('2023-06-01', 4, 5, 15, 40, NULL, NULL, NULL, 2, 7),
    ('2023-06-01', NULL, NULL, NULL, NULL, NULL, NULL, 8600, 2, 8),
    ('2023-06-01', 0, 11, 30, 60, NULL, NULL, NULL, 3, 1),
    ('2023-06-01', NULL, NULL, NULL, NULL, 5, 20, NULL, 4, 2),
    ('2023-06-01', NULL, NULL, NULL, NULL, 13, 50, NULL, 5, 3),
    ('2023-06-01', 0, 52, 14, 40, NULL, NULL, NULL, 6, 4),
    ('2023-06-01', NULL, NULL, NULL, NULL, 44, 70, NULL, 7, 5),
    ('2023-06-01', NULL, NULL, NULL, NULL, 59, 10, NULL, 8, 6),
    ('2023-06-01', 4, 15, 10, 35, NULL, NULL, NULL, 9, 7),
    ('2023-06-01', NULL, NULL, NULL, NULL, NULL, NULL, 8700, 10, 8),
    ('2023-06-01', 0, 10, 45, 70, NULL, NULL, NULL, 11, 1),
    ('2023-06-01', NULL, NULL, NULL, NULL, 6, 90, NULL, 12, 2),
    ('2023-06-01', NULL, NULL, NULL, NULL, 12, 80, NULL, 13, 3),
    ('2023-06-01', 0, 49, 15, 50, NULL, NULL, NULL, 14, 4),
    ('2023-06-01', NULL, NULL, NULL, NULL, 47, 60, NULL, 15, 5),
    ('2023-06-01', NULL, NULL, NULL, NULL, 62, 30, NULL, 16, 6),
    ('2023-06-01', 4, 12, 20, 30, NULL, NULL, NULL, 17, 7),
    ('2023-06-01', NULL, NULL, NULL, NULL, NULL, NULL, 8800, 18, 8),
    ('2023-06-01', 0, 10, 35, 55, NULL, NULL, NULL, 19, 1),
    ('2023-06-01', NULL, NULL, NULL, NULL, 7, 40, NULL, 20, 2);
