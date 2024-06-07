package br.com.deveficiente.casadocodigov2.model.compra;

import br.com.deveficiente.casadocodigov2.entity.Compra;
import br.com.deveficiente.casadocodigov2.entity.ItemPedido;
import br.com.deveficiente.casadocodigov2.entity.Pedido;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record NovoPedidoRequest(
        @NotNull
        @Positive
        BigDecimal total,
        @Size(min = 1)
        @Valid
        Set<NovoPedidoItemRequest> itens) {

        public NovoPedidoRequest(@NotNull
                                 @Positive
                                 BigDecimal total,
                                 @Size(min = 1)
                                 @Valid
                                 Set<NovoPedidoItemRequest> itens) {
                Assert.isTrue(itens.iterator().hasNext(),
                    "todo pedido deve ter pelo menos um item");
                this.total = total.setScale(2, RoundingMode.CEILING);
                this.itens = itens;
        }

        public Function<Compra, Pedido> toRequestItemPedido2(EntityManager manager) {
                Set<ItemPedido> itensCalculados = itens().stream()
                        .map(item -> item.toItemPedido(manager)).collect(Collectors.toSet());
                return (compra) -> {
                    Pedido pedido = new Pedido(compra, itensCalculados);
                    addItens(itensCalculados, pedido);
                    pedido.setTotal(pedido.calcularTotal());
                    return pedido;
                };
        }

    private void addItens(Set<ItemPedido> itensCalculados, Pedido pedido) {
        itensCalculados.forEach(it -> {
            it.setPedido(pedido);
            pedido.addItem(pedido.getItens(), Stream.of(it));
        });

        Assert.isTrue(pedido.totalIgual(total, pedido.calcularTotal()),
                "Olha, o total("+total+") enviado não corresponde ao total real("+pedido.calcularTotal()
                        .setScale(2, RoundingMode.CEILING)+").");
    }
}