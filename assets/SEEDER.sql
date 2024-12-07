USE test;

INSERT INTO Section (id, name) VALUES ('123456', 'Base de datos');

INSERT INTO Evaluation (section_id, name, date, min_points)
VALUES
('123456', 'Práctica calificada 1', CURRENT_DATE(), 11.6),
('123456', 'Práctica calificada 2', date_sub(CURRENT_DATE(), INTERVAL 1 DAY), 11.6);

INSERT INTO Student (evaluation_id, name, total_points)
VALUES
(1, 'Juan Roro Perez Robler', 15),
(1, 'Rosa Leonarda Coronado Pendeivis', 10),
(1, 'José Luis Rojas Paredes', 08),
(1, 'Ana Sofía Torres Cárdenas', 16);

INSERT INTO Question (evaluation_id, number, value)
VALUES
(1, 1, 4),
(1, 2, 4),
(1, 3, 4),
(1, 4, 4),
(1, 5, 4),
(2, 1, 5),
(2, 2, 5),
(2, 3, 5),
(2, 4, 5);

INSERT INTO Student_points (question_id, student_id, points)
VALUES
-- Student 1
(1, 1, 3),
(2, 1, 3),
(3, 1, 3),
(4, 1, 3),
(5, 1, 3),
-- Student 2
(1, 2, 2),
(2, 2, 2),
(3, 2, 2),
(4, 2, 2),
(5, 2, 2),
-- Student 3
(1, 3, 2),
(2, 3, 2),
(3, 3, -69), -- NS
(4, 3, 2),
(5, 3, 2),
-- Student 4
(1, 4, 4),
(2, 4, 4),
(3, 4, 4),
(4, 4, 4),
(5, 4, 4);


------- Check data into db -------
SELECT * FROM Section;
SELECT * FROM Evaluation;
SELECT * FROM Question;
SELECT * FROM Student;
SELECT * FROM Student_points;