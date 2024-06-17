package br.com.deveficiente.casadocodigov2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    private Livro livro;

    private int quantidade;

    public ItemPedido() {
    }

    public ItemPedido(int quantidade, Livro livro) {
        this.quantidade = quantidade;
        this.livro = livro;
    }

    public BigDecimal calcularSubtotal() {
        return this.livro.getPreco().multiply(BigDecimal.valueOf(this.quantidade));
    }
}
