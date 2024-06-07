package br.com.deveficiente.casadocodigov2.entity;

import br.com.deveficiente.casadocodigov2.model.cupom.NovoCupomRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Embeddable
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class Cupom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String codigo;

    @NotNull
    @Positive
    private BigDecimal percentualDesconto;

    @Future
    @NotNull
    private LocalDate validade;

    public Cupom(String codigo, BigDecimal percentualDesconto, LocalDate validade) {
        Assert.isTrue(!validade.isBefore(LocalDate.now()),"A validade tem que ser no futuro");
        this.codigo = codigo;
        this.percentualDesconto = percentualDesconto;
        this.validade = validade;
    }

    public Cupom(NovoCupomRequest request) {
        this.codigo = request.codigo();
        this.percentualDesconto = request.percentualDesconto();
        this.validade = request.validade();
    }

    public boolean isValid() {
        return LocalDate.now().isBefore(this.validade);
    }
}
