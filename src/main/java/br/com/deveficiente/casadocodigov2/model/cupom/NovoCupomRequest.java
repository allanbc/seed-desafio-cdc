package br.com.deveficiente.casadocodigov2.model.cupom;

import br.com.deveficiente.casadocodigov2.entity.Cupom;
import br.com.deveficiente.casadocodigov2.util.UniqueValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NovoCupomRequest (
    @NotNull
    @UniqueValue(domainClass = Cupom.class,fieldName = "codigo")
    String codigo,
    @NotNull
    @Positive
    BigDecimal percentualDesconto,
    @Future
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    LocalDate validade) {

    public Cupom toRequestCupom() {
        return new Cupom(codigo, percentualDesconto, validade);
    }
}
