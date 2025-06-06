package br.com.deveficiente.casadocodigov2.controller;

import br.com.deveficiente.casadocodigov2.entity.Pais;
import br.com.deveficiente.casadocodigov2.service.PaisService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/dominios")
public class PaisController {
    private static final Logger LOG = getLogger(PaisController.class);

    private final PaisService service;

    public PaisController(PaisService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/paises")
    public ResponseEntity<Pais> createPais(@RequestBody @Valid Pais request) {
        LOG.info("Cadastrando um país");
        return ResponseEntity.ok(service.createPais(request));
    }
    @GetMapping("/paises")
    public ResponseEntity<List<Pais>> getPaises() {
        LOG.info("Consulta de país");
        return ResponseEntity.ok(service.getPaises());
    }
}
