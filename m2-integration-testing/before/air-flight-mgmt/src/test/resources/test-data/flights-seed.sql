-- Test data for FlightRepository integration tests
-- Flights involving France (LF) and Germany (ED) with different statuses

-- Flight 1: Paris -> London, SCHEDULED
-- Departs: 2026-06-15 10:00, Arrives: 2026-06-15 11:15 (75 min)
INSERT INTO flights (
    id, flight_number,
    departure_country_code, departure_city, departure_airport_code,
    destination_country_code, destination_city, destination_airport_code,
    aircraft_id, scheduled_departure_time, duration_in_minutes,
    status, affected_by_alert_id
) VALUES (
    '11111111-1111-1111-1111-111111111111', 'AF101',
    'LF', 'Paris', 'LFPG',
    'EG', 'London', 'EGLL',
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '2026-06-15 10:00:00', 75,
    'SCHEDULED', NULL
);

-- Flight 2: London -> Paris, SCHEDULED
-- Departs: 2026-06-15 14:00, Arrives: 2026-06-15 15:20 (80 min)
INSERT INTO flights (
    id, flight_number,
    departure_country_code, departure_city, departure_airport_code,
    destination_country_code, destination_city, destination_airport_code,
    aircraft_id, scheduled_departure_time, duration_in_minutes,
    status, affected_by_alert_id
) VALUES (
    '22222222-2222-2222-2222-222222222222', 'BA202',
    'EG', 'London', 'EGLL',
    'LF', 'Paris', 'LFPG',
    'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '2026-06-15 14:00:00', 80,
    'SCHEDULED', NULL
);

-- Flight 3: Paris -> Berlin, CANCELLED
-- Departs: 2026-06-15 16:00, Arrives: 2026-06-15 17:30 (90 min)
INSERT INTO flights (
    id, flight_number,
    departure_country_code, departure_city, departure_airport_code,
    destination_country_code, destination_city, destination_airport_code,
    aircraft_id, scheduled_departure_time, duration_in_minutes,
    status, affected_by_alert_id
) VALUES (
    '33333333-3333-3333-3333-333333333333', 'AF303',
    'LF', 'Paris', 'LFPG',
    'ED', 'Berlin', 'EDDB',
    'cccccccc-cccc-cccc-cccc-cccccccccccc', '2026-06-15 16:00:00', 90,
    'CANCELLED', NULL
);

-- Flight 4: Paris -> New York, SCHEDULED (outside June 15 window)
-- Departs: 2026-06-20 08:00, Arrives: 2026-06-20 16:00 (480 min)
INSERT INTO flights (
    id, flight_number,
    departure_country_code, departure_city, departure_airport_code,
    destination_country_code, destination_city, destination_airport_code,
    aircraft_id, scheduled_departure_time, duration_in_minutes,
    status, affected_by_alert_id
) VALUES (
    '44444444-4444-4444-4444-444444444444', 'AF404',
    'LF', 'Paris', 'LFPG',
    'K', 'New York', 'KJFK',
    'dddddddd-dddd-dddd-dddd-dddddddddddd', '2026-06-20 08:00:00', 480,
    'SCHEDULED', NULL
);

-- Flight 5: Berlin -> London, SCHEDULED (not involving France)
-- Departs: 2026-06-15 12:00, Arrives: 2026-06-15 13:40 (100 min)
INSERT INTO flights (
    id, flight_number,
    departure_country_code, departure_city, departure_airport_code,
    destination_country_code, destination_city, destination_airport_code,
    aircraft_id, scheduled_departure_time, duration_in_minutes,
    status, affected_by_alert_id
) VALUES (
    '55555555-5555-5555-5555-555555555555', 'LH505',
    'ED', 'Berlin', 'EDDB',
    'EG', 'London', 'EGLL',
    'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '2026-06-15 12:00:00', 100,
    'SCHEDULED', NULL
);
