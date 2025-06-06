package br.com.deveficiente.casadocodigov2.controller;

import br.com.deveficiente.casadocodigov2.model.autor.AutorResponse;
import br.com.deveficiente.casadocodigov2.model.autor.CadastroAutorRequest;
import br.com.deveficiente.casadocodigov2.service.AutorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private static final Logger LOG = getLogger(AutorController.class);
    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutorResponse> create(@RequestBody @Valid CadastroAutorRequest request) {
        LOG.info("Cadastrando um autor");
        var autor = autorService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autor.getId())
                .toUri();
        return ResponseEntity.created(location).body(new AutorResponse(autor));
    }
    @GetMapping("/{id}")
    public ResponseEntity<AutorResponse> getById(@PathVariable("id") Long id) {
        LOG.info("Consulta de autor por id: {}", id);
        return ResponseEntity.ok(autorService.getById(id));
    }
}
