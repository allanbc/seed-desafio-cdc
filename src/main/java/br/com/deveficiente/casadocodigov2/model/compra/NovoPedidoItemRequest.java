package br.com.deveficiente.casadocodigov2.model.compra;

import br.com.deveficiente.casadocodigov2.entity.ItemPedido;
import br.com.deveficiente.casadocodigov2.entity.Livro;
import br.com.deveficiente.casadocodigov2.util.ExistsId;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NovoPedidoItemRequest(
        @NotNull
        @ExistsId(domainClass = Livro.class, fieldName = "id")
        Long idLivro,
        @Positive
        int quantidade) {
    public NovoPedidoItemRequest(@NotNull
                                 Long idLivro, @Positive
                                 int quantidade) {
        this.idLivro = idLivro;
        this.quantidade = quantidade;
    }

    public ItemPedido toItemPedido(EntityManager manager) {
        @NotNull Livro livro = manager.find(Livro.class, idLivro);
        return new ItemPedido(quantidade, livro);
    }

}

