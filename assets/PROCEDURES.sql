USE test;

DELIMITER //

DROP PROCEDURE IF EXISTS getSections //

CREATE PROCEDURE
	getSections()
BEGIN
	SELECT id, name FROM Section;
END
//

DELIMITER //

DROP PROCEDURE IF EXISTS getSectionInfo //

CREATE PROCEDURE
	getSectionInfo(section_id CHAR(6))
BEGIN
	SELECT 
        s.id AS section_id,
        s.name AS section_name,
        COUNT(e.id) AS total_evaluations,
        IFNULL(SUM(evaluation_students.total_students), 0) AS total_students
    FROM 
        Section s
    LEFT JOIN 
        Evaluation e ON s.id = e.section_id
    LEFT JOIN 
        (SELECT 
             evaluation_id, 
             COUNT(id) AS total_students 
         FROM 
             Student 
         GROUP BY 
             evaluation_id
        ) AS evaluation_students ON e.id = evaluation_students.evaluation_id
	WHERE section_id = s.id
    GROUP BY 
        s.id, s.name;
END
//

DROP PROCEDURE IF EXISTS getEvaluationsOfSection //

CREATE PROCEDURE
	getEvaluationsOfSection(section_id CHAR(6))
BEGIN
	SELECT
		e.id AS evaluation_id,
        e.name AS evaluation_name,
        (
            SELECT COUNT(*) 
            FROM Question q 
            WHERE q.evaluation_id = e.id
        ) AS question_count,
        e.min_points AS min_points,
        e.date AS evaluation_date,
        IFNULL(AVG(student_scores.total_points), 0) AS average_student_score
    FROM 
        Evaluation e
    LEFT JOIN 
        (
            SELECT 
                s.evaluation_id, 
                SUM(sp.points) AS total_points
            FROM 
                Student_points sp
            INNER JOIN 
                Student s ON sp.student_id = s.id
            GROUP BY 
                s.id
        ) AS student_scores ON e.id = student_scores.evaluation_id
	WHERE e.section_id = section_id
    GROUP BY 
        e.id;
END
//

DROP PROCEDURE IF EXISTS getQuestionsOfEvaluation //

CREATE PROCEDURE
	getQuestionsOfEvaluation(evaluation_id INT)
BEGIN
	SELECT
		id AS question_id,
        number AS question_number,
        value
    FROM Question AS Q
    WHERE Q.evaluation_id = evaluation_id;
END
//

DROP PROCEDURE IF EXISTS getStudentsNotesOfEvaluation //

CREATE PROCEDURE
	getStudentsNotesOfEvaluation(evaluation_id INT)
BEGIN
	SELECT
		S.id AS student_id,
        S.name AS student_name,
        Q.number AS question_number,
        SP.points AS student_points
    FROM Student AS S
    INNER JOIN Student_points as SP
    ON S.id = SP.student_id
    INNER JOIN Question as Q
    ON Q.id = SP.question_id
    WHERE S.evaluation_id = evaluation_id;
END
//

DROP PROCEDURE IF EXISTS deleteEvaluation //

CREATE PROCEDURE
	deleteEvaluation(evaluation_id INT)
BEGIN
	SET SQL_SAFE_UPDATES = 0;
	DELETE FROM Student_points AS ST WHERE ST.student_id IN (SELECT id FROM Student AS S WHERE S.evaluation_id = evaluation_id);
    DELETE FROM Student AS S WHERE S.evaluation_id = evaluation_id;
    DELETE FROM Question AS Q WHERE Q.evaluation_id = evaluation_id;
    DELETE FROM Evaluation AS E WHERE E.id = evaluation_id;
	SET SQL_SAFE_UPDATES = 1;
END
//

DROP PROCEDURE IF EXISTS deleteSection //

CREATE PROCEDURE
	deleteSection(section_id CHAR(6))
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE eval_id INT;
    DECLARE cur_evaluations CURSOR FOR 
        SELECT id FROM Evaluation AS E WHERE E.section_id = section_id;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur_evaluations;

	SET SQL_SAFE_UPDATES = 0;
    
    read_loop: LOOP
        FETCH cur_evaluations INTO eval_id;
        IF done THEN
            LEAVE read_loop;
        END IF;

		-- Delete the evaluation
        CALL deleteEvaluation(eval_id);
    END LOOP;
    
    SET SQL_SAFE_UPDATES = 1;

    CLOSE cur_evaluations;

    DELETE FROM Section AS S WHERE S.id = section_id;
END
//

DROP PROCEDURE IF EXISTS insertOrCreateSectionEvaluation //

CREATE PROCEDURE
	insertOrCreateSectionEvaluation(section_id CHAR(6), section_name VARCHAR(45), evaluation_name VARCHAR(45), evaluation_date DATE, evaluation_min_points DECIMAL(4,2))
BEGIN
    DECLARE existing_section_id CHAR(6);
    DECLARE new_evaluation_id INT;

    SELECT id INTO existing_section_id
    FROM Section
    WHERE id = section_id;

    IF existing_section_id IS NULL THEN
        INSERT INTO Section (id, name)
        VALUES (section_id, section_name);
    END IF;

    INSERT INTO Evaluation (section_id, name, date, min_points)
    VALUES (section_id, evaluation_name, evaluation_date, evaluation_min_points);

    SET new_evaluation_id = LAST_INSERT_ID();

    SELECT new_evaluation_id AS evaluation_id;
END
//

DROP PROCEDURE IF EXISTS insertQuestion //

CREATE PROCEDURE
	insertQuestion(evaluation_id INT, number TINYINT, value DECIMAL(4,2))
BEGIN
    DECLARE new_question_id INT;
    
    INSERT INTO Question (evaluation_id, number, value)
    VALUES (evaluation_id, number, value);
    
    SET new_question_id = LAST_INSERT_ID();
    
    SELECT new_question_id AS question_id;
END
//

DROP PROCEDURE IF EXISTS setStudentWithNotes//

CREATE PROCEDURE setStudentWithNotes(
    IN student_name VARCHAR(75),
    IN evaluation_id INT,
    IN points JSON
)
BEGIN
    DECLARE student_id INT;
    DECLARE question_id INT;
    DECLARE i INT DEFAULT 0;
    DECLARE total_questions INT;
    DECLARE question_points DECIMAL(4,2);

    SELECT id INTO student_id
    FROM Student
    WHERE name = student_name AND evaluation_id = evaluation_id
    LIMIT 1;

    IF student_id IS NULL THEN
        INSERT INTO Student (name, evaluation_id, total_points)
        VALUES (student_name, evaluation_id, 0);
        SET student_id = LAST_INSERT_ID();
    END IF;

    SET total_questions = JSON_LENGTH(points);

    WHILE i < total_questions DO
        SET @question_points_now = JSON_EXTRACT(points, CONCAT('$[', i, ']'));
        
        SELECT id INTO question_id
        FROM Question
        WHERE evaluation_id = evaluation_id AND number = i + 1
        LIMIT 1;

        INSERT INTO Student_points (student_id, question_id, points)
        VALUES (student_id, question_id, @question_points_now)
        ON DUPLICATE KEY UPDATE points = @question_points_now;

        SET i = i + 1;
    END WHILE;

    UPDATE Student
    SET total_points = (
        SELECT SUM(points)
        FROM Student_points
        WHERE student_id = Student.id
    )
    WHERE id = student_id;
END//



DELIMITER ;

SET @points = '[1.2, 5.1, 4.6]';

SELECT CAST(JSON_EXTRACT(@points, CONCAT('$[', 0, ']')) AS DECIMAL(4,2)) as aw;


CALL getSections();
CALL getSectionInfo('123456');
CALL getEvaluationsOfSection('123456');
CALL getQuestionsOfEvaluation(1);
CALL getStudentsNotesOfEvaluation(1);
CALL insertOrCreateSectionEvaluation('40934', 'Base de datos', 'PC1', '2024-12-03', 1.5);
CALL insertQuestion(3, 1, 5.0);
CALL insertQuestion(3, 2, 5.0);
CALL insertQuestion(3, 3, 5.0);
CALL setStudentWithNotes('Juan pepito wa232', 3, '[1.2, 5.1, 4.6]');
-- CALL deleteEvaluation(1);
-- CALL deleteSection('123456');