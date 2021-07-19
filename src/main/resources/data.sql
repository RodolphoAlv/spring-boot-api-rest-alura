

INSERT INTO USUARIO(nome, email, senha)
VALUES ('Aluno','aluno@email.com', '123456');

INSERT INTO CURSO(nome, categoria)
VALUES
('Spring boot','Programação'),
('HTML5', 'Front-end');

INSERT INTO TOPICO(titulo, mensagem, data_criacao, status, autor_id, curso_id)
VALUES
('Dúvida', 'Erro ao criar projeto', '2021-07-19 18:00:00', 'NAO_RESPONDIDO', 1, 1),
('Dúvida2', 'Projeto não compila', '2021-07-19 19:00:00', 'NAO_RESPONDIDO', 1, 1),
('Dúvida3', 'Tag HTML', '2021-07-19 20:00:00', 'NAO_RESPONDIDO', 1, 2);
