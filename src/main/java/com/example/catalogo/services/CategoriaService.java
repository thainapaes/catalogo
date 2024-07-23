package com.example.catalogo.services;

import com.example.catalogo.domain.Categoria;
import com.example.catalogo.domain.Produto;
import com.example.catalogo.domain.dto.request.CategoriaPatchRequestDTO;
import com.example.catalogo.domain.dto.request.CategoriaRequestDTO;
import com.example.catalogo.domain.dto.request.DesvincularProdutoRequestDTO;
import com.example.catalogo.domain.dto.request.ProdDisponivelResquestDTO;
import com.example.catalogo.domain.dto.response.CategoriaResponseDTO;
import com.example.catalogo.domain.dto.response.ProdutoResponseDTO;
import com.example.catalogo.mapper.CategoriaMapper;
import com.example.catalogo.mapper.ProdutoMapper;
import com.example.catalogo.repositories.ICategoriaRepository;
import com.example.catalogo.repositories.IProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private ICategoriaRepository categoriaRepository;
    @Autowired
    private IProdutoRepository produtoRepository;
    @Autowired
    private CategoriaMapper mapper;
    @Autowired
    private ProdutoMapper produtoMapper;

    public List<CategoriaResponseDTO> allCategorias(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Categoria> categoriaPage = categoriaRepository.findAll(pageable);

        return categoriaPage.map(categoria -> new CategoriaResponseDTO(
                categoria.getId(), categoria.getNome(), categoria.getSecao(),
                produtoMapper.toDto(categoria.getProduto_id()))).stream().toList();
    }

    public Categoria getCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não existe"));
        return categoria;
    }

    //testar
    public CategoriaResponseDTO getCategoriaDto(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não existe"));
        return mapper.toDto(categoria);
    }

    //testar
    public List<CategoriaResponseDTO> getCategorias(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Categoria> categoriaPage = categoriaRepository.findAll(pageable);

        return categoriaPage.map(categoria -> new CategoriaResponseDTO(
                categoria.getId(), categoria.getNome(), categoria.getSecao(),
                produtoMapper.toDto(categoria.getProduto_id()))).stream().toList();
    }

    public Categoria saveCategoria(CategoriaRequestDTO request) {
        Categoria response = null;
        Categoria categoriaAux = new Categoria();

        if (request.nome().isEmpty()) {
            throw new IllegalArgumentException("O nome da categoria não foi informado");
        } else if (request.secao().isEmpty()) {
            throw new IllegalArgumentException("A seção deve ser informada");
        }

        if (request.produtoId() < 0) {
            throw new IllegalArgumentException("A categoria precisa ter pelo menos um produto para ser cadastrada!");
        }

        Produto exists = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        categoriaAux.setProduto_id(exists);
        categoriaAux.setNome(request.nome());
        categoriaAux.setSecao(request.secao());

        response = categoriaRepository.save(categoriaAux);
        return response;
    }

    //testar
    public CategoriaResponseDTO saveCategoriaDto(CategoriaRequestDTO request) {
        Categoria response = null;
        Categoria categoriaAux = new Categoria();

        if (request.nome().isEmpty()) {
            throw new IllegalArgumentException("O nome da categoria não foi informado");
        } else if (request.secao().isEmpty()) {
            throw new IllegalArgumentException("A seção deve ser informada");
        }

        if (request.produtoId() < 0) {
            throw new IllegalArgumentException("A categoria precisa ter pelo menos um produto para ser cadastrada!");
        }

        Produto exists = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        categoriaAux.setProduto_id(exists);
        categoriaAux.setNome(request.nome());
        categoriaAux.setSecao(request.secao());

        response = categoriaRepository.save(categoriaAux);
        return mapper.toDto(response);
    }

    public Categoria updateCategoria(Long id, CategoriaPatchRequestDTO request) {
        Categoria existed = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não existe"));
        Categoria responseDTO = null;

        if(!request.nome().isEmpty() && !existed.getNome().equals(request.nome())) {
            existed.setNome(request.nome());
        }

        if(!request.secao().isEmpty() && !existed.getSecao().equals(request.secao())) {
            existed.setSecao(request.secao());
        }

        responseDTO = categoriaRepository.save(existed);
        return responseDTO;
    }

    public CategoriaResponseDTO updateCategoriaDto(Long id, CategoriaPatchRequestDTO request) {
        Categoria existed = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não existe"));
        Categoria responseDTO = null;

        if(!request.nome().isEmpty() && !existed.getNome().equals(request.nome())) {
            existed.setNome(request.nome());
        }

        if(!request.secao().isEmpty() && !existed.getSecao().equals(request.secao())) {
            existed.setSecao(request.secao());
        }

        responseDTO = categoriaRepository.save(existed);
        return mapper.toDto(responseDTO);
    }

    public HttpStatus deleteCategoria(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
                //.orElseThrow(() -> new IllegalArgumentException("Categoria não existe"));
        //categoriaRepository.delete(categoria);

        if(categoria.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        } else {
            categoriaRepository.delete(categoria.get());
            return HttpStatus.OK;
        }
    }

    public CategoriaResponseDTO desvincularProd(DesvincularProdutoRequestDTO dto){
        Optional<Categoria> categoria = categoriaRepository.findById(dto.idCategoria());
        if(categoria.isPresent()) {
            Categoria c = categoria.get();
            if (Objects.equals(c.getProduto_id().getId(), dto.idProduto())) {
                c.setProduto_id(null);
            }
            return mapper.toDto(categoriaRepository.save(c));
        }
        return null;
    }

}
