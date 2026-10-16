CREATE DATABASE IF NOT EXISTS mecaniqa_db;
USE mecaniqa_db;

-- Tabela das peças
CREATE TABLE IF NOT EXISTS peca (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    preco DECIMAL(10,2) NOT NULL,
    quantidade_estoque INT NOT NULL,
    categoria VARCHAR(50) NOT NULL
);

-- Tabela de Serviços
CREATE TABLE IF NOT EXISTS servico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    preco DECIMAL(10,2) NOT NULL
);

-- Tabela de Ordens de Serviço
CREATE TABLE IF NOT EXISTS ordem_servico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Pedidos de Peças
CREATE TABLE IF NOT EXISTS pedido_pecas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL
);