package br.com.deveficiente.casadocodigov2.controller;

import br.com.deveficiente.casadocodigov2.model.Autor.AutorResponse;
import br.com.deveficiente.casadocodigov2.model.Autor.CadastroAutorRequest;
import br.com.deveficiente.casadocodigov2.service.AutorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        LOG.info("Criando uma categoria");
        return ResponseEntity.ok(AutorResponse.toResponse(autorService.create(request)));
    }
}
