package br.com.deveficiente.casadocodigov2.controller;

import br.com.deveficiente.casadocodigov2.model.livro.NovoLivroRequest;
import br.com.deveficiente.casadocodigov2.model.livro.LivroDetalheResponse;
import br.com.deveficiente.casadocodigov2.model.livro.LivroResponse;
import br.com.deveficiente.casadocodigov2.service.LivroService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/livros")
public class LivroController {
    private static final Logger LOG = getLogger(LivroController.class);
    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LivroResponse> create(@RequestBody @Valid NovoLivroRequest request) {
        LOG.info("Cadastrando um livro");
        var livro = livroService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(livro.getId())
                .toUri();
        return ResponseEntity.created(location).body(new LivroResponse(livro));
    }
//    @GetMapping
//    public ResponseEntity<List<LivroResponse>> listar() {
//        return ResponseEntity.ok(livroService.listar());
//    }
    @GetMapping("/produtos/{id}")
    public ResponseEntity<LivroDetalheResponse> getByProdutoDetalhe(@PathVariable("id") Long id) {
        LOG.info("Consulta de livro por id: {}", id);
        return ResponseEntity.ok(livroService.getByProdutoDetalhe(id));
    }


}
