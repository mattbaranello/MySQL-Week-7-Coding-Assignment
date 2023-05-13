DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS step;
DROP TABLE IF EXISTS project_category;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS category;

--Creates 'project' table and columns with their respected data type assigned within table within table, with 'project_id' as the primary key

CREATE TABLE project (
	project_id INT AUTO_INCREMENT NOT NULL,
	project_name VARCHAR(128) NOT NULL,
	notes TEXT,
	difficulty INT,
	estimated_hours DECIMAL,
	actual_hours DECIMAL,
	PRIMARY KEY (project_id)
);

--Creates 'category' table and columns and their respected data type assigned within table, with 'category_id' as the primary key

CREATE TABLE category (
	category_id INT AUTO_INCREMENT NOT NULL,
	category_name VARCHAR(128) NOT NULL,
	PRIMARY KEY (category_id)
);

--Creates 'material' table and columns with their respected data type assigned within table, with 'material_id' as the primary key, as well
--as the foreign key 'project_id' referencing 'project_id' from the 'project' table

CREATE TABLE material (
	material_id INT AUTO_INCREMENT NOT NULL,
	project_id INT NOT NULL,
	material_name VARCHAR(128) NOT NULL,
	num_required INT,
	cost DECIMAL,
	PRIMARY KEY (material_id),
	FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE
);

--Creates join table 'project_category', with foreign keys 'project_id' and 'category_id' referencing their respected tables.
--'project_id' and 'category_id' are unique keys, which ensures all values in these columns are different.

CREATE TABLE project_category (
	project_id INT NOT NULL,
	category_id INT NOT NULL,
	FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE,
	FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE CASCADE,
	UNIQUE KEY (project_id, category_id) ON DELETE CASCADE
);

--Creates 'step' table and columns with their respected data type assigned within table, with 'step_id' as the primary key, as well
--as the foreign key 'project_id' referencing 'project_id' from the 'project' table

CREATE TABLE step (
	step_id INT AUTO_INCREMENT NOT NULL,
	project_id INT NOT NULL,
	step_order INT NOT NULL,
	step_text TEXT NOT NULL,
	PRIMARY KEY (step_id),
	FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE
);

