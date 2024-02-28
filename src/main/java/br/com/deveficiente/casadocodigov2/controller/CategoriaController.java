package br.com.deveficiente.casadocodigov2.controller;

import br.com.deveficiente.casadocodigov2.model.categoria.CadastroCategoriaRequest;
import br.com.deveficiente.casadocodigov2.model.categoria.CategoriaResponse;
import br.com.deveficiente.casadocodigov2.service.CategoriaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private static final Logger LOG = getLogger(CategoriaController.class);
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoriaResponse> create(@RequestBody @Valid CadastroCategoriaRequest request) {
        LOG.info("Cadastrando uma categoria");
        return ResponseEntity.ok(new CategoriaResponse(categoriaService.create(request)));
    }
}
