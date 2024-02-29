package br.com.deveficiente.casadocodigov2.controller;

import br.com.deveficiente.casadocodigov2.entity.Estado;
import br.com.deveficiente.casadocodigov2.service.EstadoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/dominios")
public class EstadoController {
    private static final Logger LOG = getLogger(EstadoController.class);

    private final EstadoService service;

    public EstadoController(EstadoService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/estados")
    public ResponseEntity<Estado> createEstado(@RequestBody @Valid Estado request) {
        LOG.info("Cadastrando um estado");
        return ResponseEntity.ok(service.createEstado(request));
    }

    @GetMapping("/estados")
    public ResponseEntity<List<Estado>> getEstados() {
        LOG.info("Consulta de estado");
        return ResponseEntity.ok(service.getEstados());
    }
}
