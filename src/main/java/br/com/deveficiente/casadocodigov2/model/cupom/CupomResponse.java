package br.com.deveficiente.casadocodigov2.model.cupom;

import br.com.deveficiente.casadocodigov2.entity.Cupom;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public record CupomResponse(
        Long id,
        String codigo,
        BigDecimal percentualDesconto,
        LocalDate validade
) {
    public CupomResponse(Cupom cupom) {
        this(cupom.getId(), cupom.getCodigo(), cupom.getPercentualDesconto(), cupom.getValidade());
    }
}
