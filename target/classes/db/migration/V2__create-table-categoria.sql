CREATE TABLE IF NOT EXISTS catalogo.categoria (
    id SERIAL PRIMARY KEY,
    cat_nome VARCHAR(100),
    cat_secao VARCHAR(100),
    produto_id INTEGER,
    FOREIGN KEY (produto_id) REFERENCES catalogo.produto(id) on DELETE CASCADE
);