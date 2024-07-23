package com.example.catalogo.repositories;

import com.example.catalogo.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByProdNome(String prodNome);

   // @Query("SELECT p FROM produto p WHERE p.disponivel = 'S'")
    //Page<Produto> findProdutosDisponiveis(Pageable pageable);
}
