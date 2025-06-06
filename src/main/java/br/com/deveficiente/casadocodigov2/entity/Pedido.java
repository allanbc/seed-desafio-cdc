package br.com.deveficiente.casadocodigov2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @OneToOne
    @JsonIgnore
    private Compra compra;

    private BigDecimal total;

    private BigDecimal totalComDesconto;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "pedido", orphanRemoval = true)
    @ElementCollection
    @JsonBackReference
    private Set<ItemPedido> itens  = new HashSet<>();

    public Pedido() {
    }

    public Pedido(@NotNull @Valid Compra compra,
                  Set<ItemPedido> itens) {
        Assert.isTrue(itens.iterator().hasNext(),
                "todo pedido deve ter pelo menos um item");
        this.compra = compra;

        this.itens.addAll(itens);
    }

    public <T> void addItem(Set<T> itens, Stream<T> source) {
        source.forEachOrdered(itens::add);
    }
    public boolean totalIgual(@Positive @NotNull BigDecimal valor, BigDecimal totalPedidoReal) {
        return totalPedidoReal.equals(valor.setScale(2, RoundingMode.UNNECESSARY));
    }
    public BigDecimal calcularTotal() {
        return this.itens.stream()
                .map(ItemPedido::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularTotalComDesconto(BigDecimal percentualDesconto) {
        BigDecimal totalComDesconto = calcularTotal();
        BigDecimal desconto = totalComDesconto.multiply(percentualDesconto).divide(new BigDecimal("100").setScale(2, RoundingMode.UNNECESSARY));
        return totalComDesconto.subtract(desconto);
    }

    public boolean totalIgual(BigDecimal valor) {
        BigDecimal totalPedido = this.calcularTotal().setScale(2, RoundingMode.HALF_UP);
        return totalPedido.compareTo(valor.setScale(2, RoundingMode.HALF_UP)) == 0;
    }
}
