package com.example.catalogo.mapper;

import com.example.catalogo.domain.Categoria;
import com.example.catalogo.domain.dto.response.CategoriaResponseDTO;
import com.example.catalogo.domain.dto.response.ProdutoResponseDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-23T03:08:10-0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class CategoriaMapperImpl implements CategoriaMapper {

    @Override
    public CategoriaResponseDTO toDto(Categoria categoria) {
        if ( categoria == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        String secao = null;

        id = categoria.getId();
        nome = categoria.getNome();
        secao = categoria.getSecao();

        ProdutoResponseDTO produtoId = null;

        CategoriaResponseDTO categoriaResponseDTO = new CategoriaResponseDTO( id, nome, secao, produtoId );

        return categoriaResponseDTO;
    }

    @Override
    public Categoria toEntity(CategoriaResponseDTO categoriaResponseDTO) {
        if ( categoriaResponseDTO == null ) {
            return null;
        }

        Categoria categoria = new Categoria();

        categoria.setId( categoriaResponseDTO.id() );
        categoria.setNome( categoriaResponseDTO.nome() );
        categoria.setSecao( categoriaResponseDTO.secao() );

        return categoria;
    }
}
