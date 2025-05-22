-- 1. USERS (password: Password1@)
INSERT INTO users (email, name, password, phone_number, user_role, is_deleted)
VALUES ('user01@test.com', '사용자01', '$2b$12$EMnSxwgbtdx8LT7lGWihqe6t0u659U1DpklO1n00hQt.h1xvE4Iqa@', '010-0000-0001',
        'USER', false),
       ('user02@test.com', '사용자02', '$2b$12$EMnSxwgbtdx8LT7lGWihqe6t0u659U1DpklO1n00hQt.h1xvE4Iqa@', '010-0000-0002',
        'USER', false),
       ('user03@test.com', '사용자03', '$2b$12$EMnSxwgbtdx8LT7lGWihqe6t0u659U1DpklO1n00hQt.h1xvE4Iqa@', '010-0000-0003',
        'USER', false),
       ('user04@test.com', '사용자04', '$2b$12$EMnSxwgbtdx8LT7lGWihqe6t0u659U1DpklO1n00hQt.h1xvE4Iqa@', '010-0000-0004',
        'USER', false),
       ('user05@test.com', '사용자05', '$2b$12$EMnSxwgbtdx8LT7lGWihqe6t0u659U1DpklO1n00hQt.h1xvE4Iqa@', '010-0000-0005',
        'USER', false),
       ('user06@test.com', '사용자06', '$2b$12$EMnSxwgbtdx8LT7lGWihqe6t0u659U1DpklO1n00hQt.h1xvE4Iqa@', '010-0000-0006',
        'USER', false),
       ('user07@test.com', '사용자07', '$2b$12$EMnSxwgbtdx8LT7lGWihqe6t0u659U1DpklO1n00hQt.h1xvE4Iqa@', '010-0000-0007',
        'USER', false),
       ('user08@test.com', '사용자08', '$2b$12$EMnSxwgbtdx8LT7lGWihqe6t0u659U1DpklO1n00hQt.h1xvE4Iqa@', '010-0000-0008',
        'USER', false),
       ('user09@test.com', '사용자09', '$2b$12$EMnSxwgbtdx8LT7lGWihqe6t0u659U1DpklO1n00hQt.h1xvE4Iqa@', '010-0000-0009',
        'USER', false),
       ('user10@test.com', '사용자10', '$2b$12$EMnSxwgbtdx8LT7lGWihqe6t0u659U1DpklO1n00hQt.h1xvE4Iqa@', '010-0000-0010',
        'USER', false);

-- 2. STATIONS
INSERT INTO station (name, region, is_active)
VALUES ('서울역', '서울', true),
       ('대전역', '대전', true),
       ('대구역', '대구', true),
       ('부산역', '부산', true);

-- 3. TRAINS
INSERT INTO train (train_number, name, type, is_active)
VALUES (1001, 'KTX-1', 'KTX', true),
       (1002, 'KTX-2', 'KTX', true),
       (1003, 'KTX-3', 'KTX', true),
       (1004, 'KTX-4', 'KTX', true),
       (1005, 'KTX-5', 'KTX', true);

-- 4. TRAIN_CARS
INSERT INTO train_car (car_number, seat_type, train_id)
VALUES (1, 'PREMIUM', 1),
       (2, 'REGULAR', 1),
       (1, 'PREMIUM', 2),
       (2, 'REGULAR', 2),
       (1, 'PREMIUM', 3),
       (2, 'REGULAR', 3),
       (1, 'PREMIUM', 4),
       (2, 'REGULAR', 4),
       (1, 'PREMIUM', 5),
       (2, 'REGULAR', 5);

-- 5. SEATS
INSERT INTO seat (seat_number, train_car_id)
VALUES ('1A', 1),
       ('1B', 1),
       ('1C', 1),
       ('1D', 1),
       ('1E', 1),
       ('2A', 2),
       ('2B', 2),
       ('2C', 2),
       ('2D', 2),
       ('2E', 2),
       ('1A', 3),
       ('1B', 3),
       ('1C', 3),
       ('1D', 3),
       ('1E', 3),
       ('2A', 4),
       ('2B', 4),
       ('2C', 4),
       ('2D', 4),
       ('2E', 4),
       ('1A', 5),
       ('1B', 5),
       ('1C', 5),
       ('1D', 5),
       ('1E', 5),
       ('2A', 6),
       ('2B', 6),
       ('2C', 6),
       ('2D', 6),
       ('2E', 6),
       ('1A', 7),
       ('1B', 7),
       ('1C', 7),
       ('1D', 7),
       ('1E', 7),
       ('2A', 8),
       ('2B', 8),
       ('2C', 8),
       ('2D', 8),
       ('2E', 8),
       ('1A', 9),
       ('1B', 9),
       ('1C', 9),
       ('1D', 9),
       ('1E', 9),
       ('2A', 10),
       ('2B', 10),
       ('2C', 10),
       ('2D', 10),
       ('2E', 10);

-- 6. ROUTES (train_id = 1만 포함)
INSERT INTO route (station_id, train_id, orders, is_enabled)
VALUES (1, 1, 1, true),
       (2, 1, 2, true),
       (3, 1, 3, true),
       (4, 1, 4, true);
