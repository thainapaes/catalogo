package com.example.catalogo.controllers;

import com.example.catalogo.domain.dto.request.ProdDisponivelResquestDTO;
import com.example.catalogo.domain.dto.request.ProdutoRequestDTO;
import com.example.catalogo.domain.dto.response.ProdutoResponseDTO;
import com.example.catalogo.services.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("catalogo/produto")
public class ProdutoController {

    private final ProdutoService service;
    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> saveProduct(@RequestBody ProdutoRequestDTO request) {
        ProdutoResponseDTO p = service.saveProduto(request);
        return p == null
                ? ResponseEntity.ofNullable(p)
                : ResponseEntity.ok(p);
    }

    @GetMapping
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<ProdutoResponseDTO>> getProdutos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<ProdutoResponseDTO> allProdutos = this.service.getProdutos(page, size);
        return ResponseEntity.ok(allProdutos);
    }

    @GetMapping("/{produtoId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<ProdutoResponseDTO> getProduto(@PathVariable Long produtoId) {
        return ResponseEntity.ok(this.service.getProduto(produtoId));
    }

    @GetMapping("/{prodNome}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<ProdutoResponseDTO> getProdutoByNome(@PathVariable String prodNome) {
        return ResponseEntity.ok(this.service.getProdutoByNome(prodNome));
    }

    @PatchMapping("/{produtoId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<ProdutoResponseDTO> updateProduto(@PathVariable Long produtoId, @RequestBody ProdutoRequestDTO request) {
        return ResponseEntity.ok(service.updateProduto(produtoId, request));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PatchMapping("/disponibilizar")
    public ResponseEntity<ProdutoResponseDTO> disponibilizarProd(@RequestBody ProdDisponivelResquestDTO request) {
        return ResponseEntity.ok(service.disponibilizarProd(request));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PatchMapping("/disponibilizarNome")
    public ResponseEntity<ProdutoResponseDTO> disponibilizarProdNome(@RequestBody ProdDisponivelResquestDTO request) {
        return ResponseEntity.ok(service.disponibilizarProdNome(request));
    }

    /*@GetMapping("/{produtosDisponiveis}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<ProdutoResponseDTO>> getProdutosDisponiveis(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<ProdutoResponseDTO> allProdutos = this.service.getProdutosDisponiveis(page, size);
        return ResponseEntity.ok(allProdutos);
    }*/

}
