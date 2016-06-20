INSERT INTO SHIFT(ID, NAME, START_TIME, END_TIME) VALUES (1, 'NIGHT', CURRENT_TIME(), DATEADD('HOUR',7, CURRENT_TIME()));
INSERT INTO SHIFT(ID, NAME, START_TIME, END_TIME) VALUES (2, 'DAY', DATEADD('HOUR', 7, CURRENT_TIME()), DATEADD('HOUR', 14, CURRENT_TIME()));
INSERT INTO SHIFT(ID, NAME, START_TIME, END_TIME) VALUES (3, 'YET ANOTHER ONE', DATEADD('HOUR', 5, CURRENT_TIME()), DATEADD('HOUR', 7, CURRENT_TIME()));