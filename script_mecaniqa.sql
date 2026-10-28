CREATE DATABASE IF NOT EXISTS mecaniqa_db;
USE mecaniqa_db;

--Tabela de Peças
CREATE TABLE IF NOT EXISTS peca(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    codigo_de_barras VARCHAR(50) NOT NULL,
    fornecedor VARCHAR(100) NOT NULL,
    qtd_estoque INT NOT NULL DEFAULT 0,
    preco_custo DECIMAL (10,2) NOT NULL,
    preco_venda DECIMAL (10,2) NOT NULL,
    categoria_peca VARCHAR(50) NOT NULL,
    cor VARCHAR(50),
    tamanho VARCHAR(50),
    data_cadastro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_ultima_atualizacao DATETIME
    );

--Tabela de Serviços
CREATE TABLE IF NOT EXISTS servico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_servico VARCHAR(100) NOT NULL,
    tempo_estimado INT NOT NULL DEFAULT 0,
    custo_tabelado DECIMAL(10,2) NOT NULL,
    data_cadastro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_ultima_atualizacao DATETIME
    );

--Tabela de Ordens de Serviços
CREATE TABLE IF NOT EXISTS ordem_servico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    data_abertura DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_fechamento DATETIME,
    status VARCHAR(50) NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    desconto DECIMAL(10,2) NOT NULL DEFAULT 0.00
    );

--Tabela de Pedidos de Peças
CREATE TABLE IF NOT EXISTS pedido_pecas(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fornecedor VARCHAR(100) NOT NULL,
    data_pedido DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_previsao DATETIME,
    status VARCHAR(50) NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    forma_pagamento VARCHAR(20),
    observacoes TEXT
    );

--TABELAS ASSOCIATIVAS

--Mapeamento do List<Peca> em OrdemServico
CREATE TABLE IF NOT EXISTS ordem_servico_pecas(
    ordem_servico_id BIGINT NOT NULL,
    peca_id BIGINT NOT NULL,
    PRIMARY KEY (ordem_servico_id, peca_id),
    CONSTRAINT fk_os_pecas FOREIGN KEY (ordem_servico_id) REFERENCES ordem_servico(id) ON DELETE CASCADE,
    CONSTRAINT fk_peca_os FOREIGN KEY (peca_id) REFERENCES peca(id) ON DELETE RESTRICT
    );

--Mapeamento do List<Servico> em OrdemServico
CREATE TABLE IF NOT EXISTS ordem_servico_servicos(
    ordem_servico_id BIGINT NOT NULL,
    servico_id BIGINT NOT NULL,
    PRIMARY KEY (ordem_servico_id, servico_id),
    CONSTRAINT fk_os_servicos FOREIGN KEY (ordem_servico_id) REFERENCES ordem_servico(id) ON DELETE CASCADE,
    CONSTRAINT fk_servicos_os FOREIGN KEY (servico_id) REFERENCES servico(id) ON DELETE RESTRICT
    );

-- Tabela de Itens do Pedido
CREATE TABLE IF NOT EXISTS itens_pedido_peca (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_pecas_id BIGINT NOT NULL,
    peca_id BIGINT NOT NULL,
    quantidade INT NOT NULL,
    CONSTRAINT fk_pedido FOREIGN KEY (pedido_pecas_id) REFERENCES pedido_pecas(id) ON DELETE CASCADE,
    CONSTRAINT fk_peca FOREIGN KEY (peca_id) REFERENCES peca(id) ON DELETE RESTRICT
    );