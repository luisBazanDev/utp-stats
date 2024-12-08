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



DELIMITER ;

CALL getSections();
CALL getSectionInfo('123456');
CALL getEvaluationsOfSection('123456');
CALL getQuestionsOfEvaluation(1);
CALL getStudentsNotesOfEvaluation(1);
-- CALL deleteEvaluation(1);
-- CALL deleteSection('123456');