package br.com.deveficiente.casadocodigov2.entity;

import br.com.deveficiente.casadocodigov2.util.Generated;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Embeddable
public class CupomAplicado {
    @ManyToOne
    private Cupom cupom;
    @Positive
    @NotNull
    private BigDecimal percentualDescontoMomento;
    @NotNull
    @Future
    private LocalDate validadeMomento;

    @Deprecated
    @Generated(Generated.ECLIPSE)
    public CupomAplicado() {
    }
    public CupomAplicado(Cupom cupom) {
        this.cupom = cupom;
        this.percentualDescontoMomento = cupom.getPercentualDesconto();
        this.validadeMomento = cupom.getValidade();
    }
}
