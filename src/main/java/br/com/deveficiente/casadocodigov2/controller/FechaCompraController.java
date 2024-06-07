package br.com.deveficiente.casadocodigov2.controller;

import br.com.deveficiente.casadocodigov2.model.compra.CompraResponse;
import br.com.deveficiente.casadocodigov2.model.compra.NovaCompraRequest;
import br.com.deveficiente.casadocodigov2.service.CompraService;
import br.com.deveficiente.casadocodigov2.util.CupomValidoValidator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/compras")
public class FechaCompraController {

    private static final Logger LOG = getLogger(FechaCompraController.class);

    private  final CupomValidoValidator cupomValidoValidator;
    private final CompraService service;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(cupomValidoValidator);
    }

    public FechaCompraController(CupomValidoValidator cupomValidoValidator, CompraService service) {
        this.cupomValidoValidator = cupomValidoValidator;
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompraResponse> cria(@RequestBody @Valid NovaCompraRequest request) {
        LOG.info("Criando uma compra...");
        return ResponseEntity.ok(new CompraResponse(service.compra(request)));
    }

}
