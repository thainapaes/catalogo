package com.example.catalogo.mapper;

import com.example.catalogo.domain.Produto;
import com.example.catalogo.domain.dto.response.ProdutoResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    ProdutoResponseDTO toDto(Produto produto);
    Produto toEntity(ProdutoResponseDTO produtoResponseDTO);
}
