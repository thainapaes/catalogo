package com.example.catalogo.mapper;

import com.example.catalogo.domain.Categoria;
import com.example.catalogo.domain.dto.response.CategoriaResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaResponseDTO toDto(Categoria categoria);
    Categoria toEntity(CategoriaResponseDTO categoriaResponseDTO);


}
