-- employees --
INSERT INTO employees (name, role, email, start_date) VALUES
('John Doe', 'Manager', 'john.de@example.com', '2023-01-01'),
('Jane Smith', 'Developer', 'jane.smith@example.com', '2023-02-15'),
('Bob Johnson', 'Designer', 'bob.johnson@example.com', '2023-03-10'),
('Alice Williams', 'Analyst', 'alice.williams@example.com', '2023-04-05'),
('Charlie Brown', 'Engineer', 'charlie.brown@example.com', '2023-05-20');

-- contacts --
INSERT INTO contacts (name, role, email, phone_number) VALUES
('Ben', 'IT support', 'ben.ben@example.com', '1234567890'),
('Robert Smith', 'Customer Support', 'robert.smith@example.com', '2345678901'),
('Emily Davis', 'Marketing Specialist', 'emily.davis@example.com', '3456789012');

-- items --
INSERT INTO items (title, description, due_date, is_complete,owner_id, contacts_id) VALUES
('Sign Contract', 'Review and sign the new client contract', '2023-06-01 12:00:00', 0, 1, 1),
('Training Session', 'Attend the training session on new software', '2023-06-15 10:30:00', 0, 1, 1),
('Photo', 'Get photo for ID card', '2023-06-15 10:30:00', 1, 2, 1),
('Laptop Setup', 'Set up the new laptop for the team member', '2023-06-20 15:45:00', 0, 1, 3);

-- roles --
insert into roles (role_id, name) values
(1, 'ADMIN'),
(2, 'USER'),
(3, 'TEMP');

-- users --
insert into users (username, password, email, enabled, role_id) values
('admin','$2a$12$M0lid7vdMoieRAuqnqcfuucSip3Ab6AkSdn.U3oBYhmFSln.wjosq','example@email.com', true, 1),
('ben', '$2a$12$ng.tFLtiCahEtK.2Czw.5e6YsEaxh9PRgjup83yeUe0AqzDwSre6y','test@gmail.com', true, 2),
('alan', '$2a$12$ng.tFLtiCahEtK.2Czw.5e6YsEaxh9PRgjup83yeUe0AqzDwSre6y','SanaqulovD@cardiff.ac.uk', true, 2),
('charlotte', '$2a$12$X.Re0lEnjeR8l41HNEEsfOfDelYKG.8Ldu/tQw.7Xfy9ZZ1GeeK5e','treemail@hojo.com', true, 2);

INSERT INTO templates (name) VALUES
('Software Engineer');

INSERT INTO items (title, description, due_date, is_complete, owner_id, contacts_id) VALUES
('Setup workstation', 'Setup employee workstation', '2023-06-01 12:00:00', 0, 'tmp1', 1),
('Create git', 'Create git account', '2023-06-15 10:30:00', 0, 'tmp1', 1),
('Teach code', 'Teach how to code', '2023-06-15 10:30:00', 1, 'tmp1', 1);

# bcrypt encrypted hash
