package com.example.catalogo.mapper;

import com.example.catalogo.domain.Produto;
import com.example.catalogo.domain.dto.response.ProdutoResponseDTO;
import java.util.Date;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-23T13:36:21-0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class ProdutoMapperImpl implements ProdutoMapper {

    @Override
    public ProdutoResponseDTO toDto(Produto produto) {
        if ( produto == null ) {
            return null;
        }

        Long id = null;
        String prodNome = null;
        String preco = null;
        Date dataLote = null;
        String tipo = null;
        String disponivel = null;

        id = produto.getId();
        prodNome = produto.getProdNome();
        preco = produto.getPreco();
        dataLote = produto.getDataLote();
        tipo = produto.getTipo();
        disponivel = produto.getDisponivel();

        ProdutoResponseDTO produtoResponseDTO = new ProdutoResponseDTO( id, prodNome, preco, dataLote, tipo, disponivel );

        return produtoResponseDTO;
    }

    @Override
    public Produto toEntity(ProdutoResponseDTO produtoResponseDTO) {
        if ( produtoResponseDTO == null ) {
            return null;
        }

        Produto produto = new Produto();

        produto.setId( produtoResponseDTO.id() );
        produto.setProdNome( produtoResponseDTO.prodNome() );
        produto.setPreco( produtoResponseDTO.preco() );
        produto.setDataLote( produtoResponseDTO.dataLote() );
        produto.setDisponivel( produtoResponseDTO.disponivel() );
        produto.setTipo( produtoResponseDTO.tipo() );

        return produto;
    }
}
