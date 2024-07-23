package com.example.catalogo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Table(name = "categoria", schema = "catalogo")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cat_nome")
    private String nome;
    @Column(name = "cat_secao")
    private String secao;
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto_id;
}
