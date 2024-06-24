package br.com.deveficiente.casadocodigov2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
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

    @FutureOrPresent
    @NotNull
    private LocalDate validade;

    public Cupom() {
    }

    public Cupom(@NotNull String codigo, @NotNull @Positive BigDecimal percentualDesconto, @FutureOrPresent @NotNull LocalDate validade) {
        Assert.isTrue(validade.compareTo(LocalDate.now()) >= 0,"A validade tem que ser no futuro");
        this.codigo = codigo;
        this.percentualDesconto = percentualDesconto;
        this.validade = validade;
    }
    public boolean isValid() {
        return LocalDate.now().compareTo(this.validade) <= 0;
    }
}
