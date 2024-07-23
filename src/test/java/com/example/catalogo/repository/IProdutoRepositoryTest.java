package com.example.catalogo.repository;

import com.example.catalogo.domain.Produto;
import com.example.catalogo.domain.dto.request.CategoriaRequestDTO;
import com.example.catalogo.domain.dto.request.ProdutoRequestDTO;
import com.example.catalogo.repositories.IProdutoRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class IProdutoRepositoryTest {
    @Autowired
    IProdutoRepository produtoRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Produto successfully from DB")
    void findByProdNomeSuccess() {
        String prodNome = "Celular";
        ProdutoRequestDTO produto = new ProdutoRequestDTO(prodNome, "998,9", "2024-01-10", "Motorola");
        this.createProduto(produto);

        Optional<Produto> result = this.produtoRepository.findByProdNome(prodNome);
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Produto from DB when produto not exists")
    void findByProdNomeFailed() {
        String prodNome = "Celular Mobile";

        Optional<Produto> result = this.produtoRepository.findByProdNome(prodNome);
        assertThat(result.isEmpty()).isTrue();
    }

    private Produto createProduto(ProdutoRequestDTO data){
        Produto newProduto = new Produto(data);
        this.entityManager.persist(newProduto);
        return newProduto;
    }
}
