-- SQL statements for ToDoList entity
CREATE TABLE ToDoList (
                          id SERIAL PRIMARY KEY,
                          created_date TIMESTAMPTZ,
                          modified_date TIMESTAMPTZ,
                          name VARCHAR(255),
                          scheduled_date TIMESTAMPTZ,
                          status VARCHAR(20) CHECK (status IN ('CREATED', 'SCHEDULED', 'DONE'))
);

-- SQL statements for ToDoItem entity
CREATE TABLE ToDoItem (
                          id SERIAL PRIMARY KEY,
                          to_do_list_id BIGINT REFERENCES ToDoList(id),
                          order INT,
                          description VARCHAR(255),
                          completed BOOLEAN
);
