package br.com.deveficiente.casadocodigov2.controller;

import br.com.deveficiente.casadocodigov2.model.Autor.AutorResponse;
import br.com.deveficiente.casadocodigov2.model.Autor.CadastroAutorRequest;
import br.com.deveficiente.casadocodigov2.model.Categoria.CadastroCategoriaRequest;
import br.com.deveficiente.casadocodigov2.model.Categoria.CategoriaResponse;
import br.com.deveficiente.casadocodigov2.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoriaResponse> create(@RequestBody @Valid CadastroCategoriaRequest request) {
        return ResponseEntity.ok(CategoriaResponse.toResponse(categoriaService.create(request)));
    }
}
