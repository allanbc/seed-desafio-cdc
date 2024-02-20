package br.com.deveficiente.casadocodigov2.controller;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import br.com.deveficiente.casadocodigov2.model.AutorResponse;
import br.com.deveficiente.casadocodigov2.model.CadastroAutorRequest;
import br.com.deveficiente.casadocodigov2.service.AutorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/autores")
public class CadastroAutorController {

    private static final Logger LOG = getLogger(CadastroAutorController.class);
    private final AutorService autorService;

    public CadastroAutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutorResponse> cadastrar(@RequestBody @Valid CadastroAutorRequest request) {
            var autorResponse = AutorResponse.toResponse(autorService.create(request));
            return ResponseEntity.ok(autorResponse);
    }
}
