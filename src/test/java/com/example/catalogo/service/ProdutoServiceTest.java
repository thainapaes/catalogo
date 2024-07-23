package com.example.catalogo.service;

import com.example.catalogo.domain.Produto;
import com.example.catalogo.domain.dto.request.ProdDisponivelResquestDTO;
import com.example.catalogo.domain.dto.request.ProdutoRequestDTO;
import com.example.catalogo.domain.dto.response.ProdutoResponseDTO;
import com.example.catalogo.mapper.ProdutoMapper;
import com.example.catalogo.repositories.IProdutoRepository;
import com.example.catalogo.services.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class ProdutoServiceTest {
    @Mock
    private IProdutoRepository repository;

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoMapper produtoMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create the Produto successfully when everything is ok")
    void saveProductSuccess() {
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO("Celular", "998,9", "2024-01-10", "Motorola");

        Produto produto = new Produto(requestDTO);

        ProdutoResponseDTO responseDTO = new ProdutoResponseDTO(
                produto.getId(), produto.getProdNome(), produto.getPreco(),
                produto.getDataLote(), produto.getTipo(), produto.getDisponivel());

        when(repository.findByProdNome(requestDTO.prodNome())).thenReturn(Optional.empty());
        when(produtoMapper.toDto(produto)).thenReturn(responseDTO);
        when(repository.save(any(Produto.class))).thenReturn(produto);

        ProdutoResponseDTO result = this.produtoService.saveProduto(requestDTO);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should return null when any required field is empty")
    void saveProductFailedDueToEmptyField() {
        ProdutoRequestDTO requestDTOWithEmptyNome = new ProdutoRequestDTO("", "998,9", "2024-01-10", "Motorola");

        ProdutoRequestDTO requestDTOWithEmptyPrice = new ProdutoRequestDTO("Celular", "", "2024-01-10", "Motorola");

        ProdutoRequestDTO requestDTOWithEmptyType = new ProdutoRequestDTO("Celular", "998,9", "2024-01-10", "");

        when(repository.findByProdNome("Celular")).thenReturn(Optional.empty());

        Exception thrownProdNome = assertThrows(IllegalArgumentException.class, () -> {
            this.produtoService.saveProduto(requestDTOWithEmptyNome);
        });
        assertEquals("O campo nome deve ser preenchido", thrownProdNome.getMessage());

        Exception thrownPreco = assertThrows(IllegalArgumentException.class, () -> {
            this.produtoService.saveProduto(requestDTOWithEmptyPrice);
        });
        assertEquals("O campo de preço deve ser preenchido", thrownPreco.getMessage());

        Exception thrownTipo = assertThrows(IllegalArgumentException.class, () -> {
            this.produtoService.saveProduto(requestDTOWithEmptyType);
        });
        assertEquals("O campo tipo deve ser preenchido", thrownTipo.getMessage());
    }

    @Test
    @DisplayName("Should update the Produto successfully when everything is ok")
    void updateTheProductWithSuccess() {
        Produto produtoC = new Produto(1L, "Celular", "989,9", new Date(), "S", "Motorola");
        produtoC.setTipo("IPhone");
        produtoC.setPreco("990");

        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO("Celular", "998,9", "2024-01-10", "Motorola");
        ProdutoResponseDTO responseDTO = new ProdutoResponseDTO(
                produtoC.getId(), produtoC.getProdNome(), produtoC.getPreco(),
                produtoC.getDataLote(), produtoC.getTipo(), produtoC.getDisponivel());

        when(repository.findById(produtoC.getId())).thenReturn(Optional.of(produtoC));
        when(repository.save(produtoC)).thenReturn(produtoC);
        when(produtoMapper.toDto(produtoC)).thenReturn(responseDTO);

        ProdutoResponseDTO result = this.produtoService.updateProduto(1L, requestDTO);
        assertEquals(result.tipo(), "IPhone");
        assertEquals(result.preco(), "990");
    }

    @Test
    @DisplayName("Should enable the Produto")
    void changeTheStatusToEnableProduct() {
        Produto produto = new Produto(1L, "Celular", "989,9", new Date(), "S", "Motorola");
        produto.setDisponivel("N");

        ProdDisponivelResquestDTO disponivelDto = new ProdDisponivelResquestDTO(produto.getId(), "Celular");
        ProdutoResponseDTO responseDTO = new ProdutoResponseDTO(
                produto.getId(), produto.getProdNome(), produto.getPreco(),
                produto.getDataLote(), produto.getTipo(), produto.getDisponivel());

        when(repository.findById(produto.getId())).thenReturn(Optional.of(produto));
        when(repository.save(produto)).thenReturn(produto);
        when(produtoMapper.toDto(produto)).thenReturn(responseDTO);

        ProdutoResponseDTO result = this.produtoService.disponibilizarProd(disponivelDto);
        assertNotNull(result);
        assertEquals(result.disponivel(), "N");
    }
}
