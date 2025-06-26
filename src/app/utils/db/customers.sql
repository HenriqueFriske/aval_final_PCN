/**
 * Author:  Eldair
 * Created: 09 de jun. de 2025
 */

-- ******************** INSTRUÇÕES ********************
-- Criar um novo banco de dados no DerbyDB com as seguinte definições: 
-- -- nome do banco : avaliacao_II
-- -- user          : adm
-- -- password      : adm
-- dentro do banco de dados "avaliacao_II", criar a tabela "tb_customers"

/***** TABELA tb_customers *****/
DROP TABLE tb_customers;
CREATE TABLE tb_customers (
  id INTEGER NOT NULL PRIMARY KEY,
  nome varchar(100),
  cpf varchar (20),
  email varchar(200),  
  telefone varchar(30),
  celular varchar(30),
  cep varchar(100),
  rua varchar (255),
  numero int,
  bairro varchar (100),
  cidade varchar (100),
  estado varchar (2)
);