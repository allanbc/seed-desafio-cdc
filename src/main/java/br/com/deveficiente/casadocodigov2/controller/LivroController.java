package br.com.deveficiente.casadocodigov2.controller;

import br.com.deveficiente.casadocodigov2.model.autor.AutorResponse;
import br.com.deveficiente.casadocodigov2.model.livro.CadastroLivroRequest;
import br.com.deveficiente.casadocodigov2.model.livro.LivroDetalheResponse;
import br.com.deveficiente.casadocodigov2.model.livro.LivroResponse;
import br.com.deveficiente.casadocodigov2.service.LivroService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<LivroResponse> create(@RequestBody @Valid CadastroLivroRequest request) {
        LOG.info("Cadastrando um livro");
        return ResponseEntity.ok(new LivroResponse(livroService.create(request)));
    }
    @GetMapping
    public ResponseEntity<List<LivroResponse>> listar() {
        return ResponseEntity.ok(livroService.listar());
    }
    @GetMapping("/produtos/{id}")
    public ResponseEntity<LivroDetalheResponse> getByProdutoDetalhe(@PathVariable Long id) {
        LOG.info("Consulta de autor por id: {}", id);
        return ResponseEntity.ok(livroService.getByProdutoDetalhe(id));
    }


}
