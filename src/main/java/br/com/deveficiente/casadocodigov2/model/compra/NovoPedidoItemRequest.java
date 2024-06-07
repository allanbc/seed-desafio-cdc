package br.com.deveficiente.casadocodigov2.model.compra;

import br.com.deveficiente.casadocodigov2.entity.ItemPedido;
import br.com.deveficiente.casadocodigov2.entity.Livro;
import br.com.deveficiente.casadocodigov2.entity.Pedido;
import br.com.deveficiente.casadocodigov2.repository.LivroRepository;
import br.com.deveficiente.casadocodigov2.util.ExistsId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public ItemPedido toItemPedido(LivroRepository livroRepository) {
        Livro livro = livroRepository.findById(idLivro())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        return new ItemPedido(quantidade, livro);
    }

}

