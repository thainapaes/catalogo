package com.example.catalogo.domain.dto.response;

import java.util.Date;

public record ProdutoResponseDTO(Long id, String prodNome, String preco, Date dataLote, String tipo, String disponivel) {
}
