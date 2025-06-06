package br.com.deveficiente.casadocodigov2.controller;

import br.com.deveficiente.casadocodigov2.model.cupom.CupomResponse;
import br.com.deveficiente.casadocodigov2.model.cupom.NovoCupomRequest;
import br.com.deveficiente.casadocodigov2.service.CupomService;
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
@RequestMapping("/cupom")
public class CupomController {

    private static final Logger LOG = getLogger(CupomController.class);

    private final CupomService cupomService;

    public CupomController(CupomService cupomService) {
        this.cupomService = cupomService;
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CupomResponse> cria(@RequestBody @Valid NovoCupomRequest request) {
        LOG.info("Gerando um cupom...");
        return ResponseEntity.ok(new CupomResponse(cupomService.gerar(request)));
    }

}
