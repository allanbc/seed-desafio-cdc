package br.com.deveficiente.casadocodigov2.entity;

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

    @OneToOne
    private Compra compra;

    private BigDecimal total;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "pedido", orphanRemoval = true)
    @ElementCollection
    private Set<ItemPedido> itens  = new HashSet<>();

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
        return totalPedidoReal.equals(valor.setScale(2, RoundingMode.CEILING));
    }
    public BigDecimal calcularTotal() {
        return this.itens.stream()
                .map(ItemPedido::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
