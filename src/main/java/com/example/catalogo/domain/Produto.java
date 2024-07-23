package com.example.catalogo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "produto", schema = "catalogo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "prod_nome")
    private String prodNome;
    @Column(name = "prod_preco")
    private String preco;
    @Column(name = "data_lote")
    private Date dataLote;
    private String disponivel;
    @Column(name = "prod_tipo")
    private String tipo;
}
