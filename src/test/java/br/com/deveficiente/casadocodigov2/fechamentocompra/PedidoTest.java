package br.com.deveficiente.casadocodigov2.fechamentocompra;

import br.com.deveficiente.casadocodigov2.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.function.Function;

public class PedidoTest {
    @DisplayName("verifica se o total do pedido é igual ao passado como argumento")
    @ParameterizedTest
    @CsvSource({
            "10,true",
            "9.99,false",
            "10.01,false"
    })
    void testVerificaSeTotalPedidoEIgualInformadoNoArgumento(BigDecimal valor, boolean resultadoEsperado) throws Exception {
        Autor autor = new Autor("nome", "email@email.com", "descricao");
        Categoria categoria = new Categoria("categoria");
        Livro livro = new Livro("titulo", "resumo", "sumario", BigDecimal.TEN, 100, "98754985743", LocalDate.of(2000, 10, 10), autor, categoria);
        Set<ItemPedido> itens = Set.of(new ItemPedido(livro, 1));

        Pais pais = new Pais("brasil");
        Function<Compra, Pedido> funcaoCriaPedido = compra -> new Pedido(compra, itens);

        Compra compra = new Compra("email@email", "nome", "sobrenome", "58495784", "endereco", "complemento", pais, "7654965437", "89567453", funcaoCriaPedido);

        Pedido pedido = funcaoCriaPedido.apply(compra);
        var total = pedido.totalIgual(valor);
        Assertions.assertEquals(resultadoEsperado, total);
    }
}
