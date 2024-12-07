USE test;

CREATE TABLE IF NOT EXISTS Section (
	id CHAR(6) NOT NULL PRIMARY KEY,
    name VARCHAR(75) NOT NULL
);

CREATE TABLE IF NOT EXISTS Evaluation (
	id INT AUTO_INCREMENT PRIMARY KEY,
    section_id CHAR(6) NOT NULL,
    name VARCHAR(45) NOT NULL,
    date DATE NULL,
    min_points DECIMAL(4,2) NOT NULL,
    FOREIGN KEY(section_id) REFERENCES Section(id)
);

CREATE TABLE IF NOT EXISTS Student (
	id INT AUTO_INCREMENT PRIMARY KEY,
    evaluation_id INT NOT NULL,
    total_points DECIMAL(4,2),
    name VARCHAR(75) NOT NULL,
    FOREIGN KEY(evaluation_id) REFERENCES Evaluation(id)
);

CREATE TABLE IF NOT EXISTS Question (
	id INT AUTO_INCREMENT PRIMARY KEY,
    evaluation_id INT NOT NULL,
    number TINYINT NOT NULL,
    value DECIMAL(4,2),
    FOREIGN KEY(evaluation_id) REFERENCES Evaluation(id),
    UNIQUE KEY `question_id` (evaluation_id, number)
);

CREATE TABLE IF NOT EXISTS Student_points (
	question_id INT NOT NULL,
    student_id INT NOT NULL,
    points DECIMAL(4,2),
    FOREIGN KEY(question_id) REFERENCES Question(id),
    FOREIGN KEY(student_id) REFERENCES Student(id),
    UNIQUE KEY `student_points_id` (question_id, student_id)
);