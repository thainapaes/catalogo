CREATE SCHEMA IF NOT EXISTS catalogo;
CREATE TABLE IF NOT EXISTS catalogo.produto (
    id SERIAL PRIMARY KEY,
    prod_nome VARCHAR(100),
    prod_preco VARCHAR(100),
    data_lote TIMESTAMP WITHOUT TIME ZONE,
    disponivel VARCHAR(1),
    prod_tipo VARCHAR(255)
);