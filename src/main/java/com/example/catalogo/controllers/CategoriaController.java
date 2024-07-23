package com.example.catalogo.controllers;

import com.example.catalogo.domain.Categoria;
import com.example.catalogo.domain.dto.request.CategoriaPatchRequestDTO;
import com.example.catalogo.domain.dto.request.CategoriaRequestDTO;
import com.example.catalogo.domain.dto.request.DesvincularProdutoRequestDTO;
import com.example.catalogo.domain.dto.request.ProdDisponivelResquestDTO;
import com.example.catalogo.domain.dto.response.CategoriaResponseDTO;
import com.example.catalogo.domain.dto.response.ProdutoResponseDTO;
import com.example.catalogo.services.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("catalogo/categoria")
public class CategoriaController {

    private CategoriaService service;
    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<CategoriaResponseDTO> allCategorias(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return service.allCategorias(page, size);
    }

    @GetMapping("/{categoriaId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Categoria getCategoria(@PathVariable Long categoriaId) {
        return service.getCategoria(categoriaId);
    }

    @PostMapping
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Categoria> saveCategoria(@RequestBody CategoriaRequestDTO request) {
        Categoria c = service.saveCategoria(request);
        return c == null
                ? ResponseEntity.ofNullable(c)
                : ResponseEntity.ok(c);
    }

    @PatchMapping("/{categoriaId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long categoriaId, @RequestBody CategoriaPatchRequestDTO request) {
        Categoria c = service.updateCategoria(categoriaId, request);
        return c == null
                ? ResponseEntity.ofNullable(c)
                : ResponseEntity.ok(c);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PatchMapping("/desvincular")
    public ResponseEntity<CategoriaResponseDTO> desvincular(@RequestBody DesvincularProdutoRequestDTO request) {
        return ResponseEntity.ok(service.desvincularProd(request));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{categoriaId}")
    public void deleteCategoria(@PathVariable Long categoriaId) {
        service.deleteCategoria(categoriaId);
    }

}
