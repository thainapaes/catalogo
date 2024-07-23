package com.example.catalogo.domain;

import com.example.catalogo.domain.dto.request.ProdutoRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    public Produto(ProdutoRequestDTO data) {
        this.prodNome = data.prodNome();
        this.preco = data.preco();
        this.tipo = data.tipo();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateLocal = LocalDate.parse(data.dataLote(), formatter);
        this.dataLote = Date.from(dateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());

        this.disponivel = "S";
    }
}
