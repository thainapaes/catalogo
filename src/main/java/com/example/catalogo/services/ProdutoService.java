package com.example.catalogo.services;

import com.example.catalogo.domain.Produto;
import com.example.catalogo.domain.dto.request.ProdDisponivelResquestDTO;
import com.example.catalogo.domain.dto.request.ProdutoRequestDTO;
import com.example.catalogo.domain.dto.response.ProdutoResponseDTO;
import com.example.catalogo.mapper.ProdutoMapper;
import com.example.catalogo.repositories.IProdutoRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private IProdutoRepository produtoRepository;
    @Autowired
    private ProdutoMapper mapper;

    @SneakyThrows
    public ProdutoResponseDTO saveProduto(ProdutoRequestDTO request){
        Produto response = null;
        Produto produtoAux = new Produto();
        if (request.prodNome().isEmpty()) {
            throw new IllegalArgumentException("O campo nome deve ser preenchido");
        } else if (request.preco().isEmpty()) {
            throw new IllegalArgumentException("O campo de preço deve ser preenchido");
        } else if (request.tipo().isEmpty()) {
            throw new IllegalArgumentException("O campo tipo deve ser preenchido");
        }

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //LocalDate loteLocal = LocalDate.parse(request.dataLote(), formatter);
        //Date lote = Date.from(loteLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Date lote = convertDate(request.dataLote());

        if (lote.after(new Date())) {
            throw new IllegalArgumentException("O campo data precisa ser válido ou preenchido");
        }

        produtoAux.setProdNome(request.prodNome());
        produtoAux.setPreco(request.preco());
        produtoAux.setTipo(request.tipo());
        produtoAux.setDataLote(lote);
        produtoAux.setDisponivel("S");

        response = produtoRepository.save(produtoAux);
        return mapper.toDto(response);
    }

    public ProdutoResponseDTO getProduto(Long id) {
        Produto response = produtoRepository.findById(id)
                   .orElseThrow(() -> new IllegalArgumentException("Produto não existe"));
        return mapper.toDto(response);
    }

    public ProdutoResponseDTO getProdutoByNome(String prodNome) {
        Produto response = produtoRepository.findByProdNome(prodNome)
                .orElseThrow(() -> new IllegalArgumentException("Produto não existe"));
        return mapper.toDto(response);
    }

    public List<ProdutoResponseDTO> getProdutos(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Produto> produtoPage = this.produtoRepository.findAll(pageable);

        return produtoPage.map(produto -> new ProdutoResponseDTO(
                produto.getId(), produto.getProdNome(), produto.getPreco(),
                produto.getDataLote(), produto.getTipo(), produto.getDisponivel())).stream().toList();
    }

    public ProdutoResponseDTO updateProduto(Long id, ProdutoRequestDTO request) {
        Produto existed = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não existe"));
        Produto responseDTO = null;

        if(!request.prodNome().isEmpty() && !existed.getProdNome().equals(request.prodNome())) {
            existed.setProdNome(request.prodNome());
        }

        if(!request.preco().isEmpty() && !existed.getPreco().equals(request.preco())) {
            existed.setPreco(request.preco());
        }

        if(!request.tipo().isEmpty() && !existed.getTipo().equals(request.tipo())) {
            existed.setTipo(request.tipo());
        }

        Date lote = convertDate(request.dataLote());
        if (!lote.after(new Date()) && !existed.getDataLote().equals(lote)) {
            existed.setDataLote(lote);
        }

        responseDTO = produtoRepository.save(existed);
        return mapper.toDto(responseDTO);
    }

    public ProdutoResponseDTO disponibilizarProd(ProdDisponivelResquestDTO disponivelDto){
        Optional<Produto> produto = produtoRepository.findById(disponivelDto.id());
        if(produto.isPresent()) {
            Produto p = produto.get();
            if(p.getDisponivel().equals("S")) {
                p.setDisponivel("N");
            } else {
                p.setDisponivel("S");
            }
            return mapper.toDto(produtoRepository.save(p));
        }
        return null;
    }

    public ProdutoResponseDTO disponibilizarProdNome(ProdDisponivelResquestDTO disponivelDto){
        Optional<Produto> produto = produtoRepository.findByProdNome(disponivelDto.nome());
        if(produto.isPresent()) {
            Produto p = produto.get();
            if(p.getDisponivel().equals("S")) {
                p.setDisponivel("N");
            } else {
                p.setDisponivel("S");
            }
            return mapper.toDto(produtoRepository.save(p));
        }
        return null;
    }

    private Date convertDate(String data) {
        String[] separacao = data.split("T");
        if (separacao.length > 1) {
            data = data.split("T")[0];
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateLocal = LocalDate.parse(data, formatter);
        Date date = Date.from(dateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }
}
