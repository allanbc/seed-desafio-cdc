package br.com.deveficiente.casadocodigov2.builder;

import br.com.deveficiente.casadocodigov2.model.compra.NovaCompraRequest;
import br.com.deveficiente.casadocodigov2.model.compra.NovoPedidoRequest;

public class CompraRequestBuilder {

    private CompraRequestBuilder() {}
    public static NovaCompraRequest comCupomSemEstado(
            String email,
            String nome,
            String sobrenome,
            String documento,
            String endereco,
            String complemento,
            String cidade,
            Long idPais,
            String telefone,
            String cep,
            NovoPedidoRequest pedido,
            String codigoCupom
    ) {

        return new NovaCompraRequest(
                email, nome, sobrenome, documento, endereco, complemento, cidade,
                idPais, null, telefone, cep, pedido, codigoCupom
        );
    }

    public static NovaCompraRequest comEstadoECupom(
            String email,
            String nome,
            String sobrenome,
            String documento,
            String endereco,
            String complemento,
            String cidade,
            Long idPais,
            Long idEstado,
            String telefone,
            String cep,
            NovoPedidoRequest pedido,
            String codigoCupom) {

        return new NovaCompraRequest(
                email, nome, sobrenome, documento, endereco, complemento, cidade,
                idPais, idEstado, telefone, cep, pedido, codigoCupom
        );
    }

    public static NovaCompraRequest semEstadoECupom(
            String email,
            String nome,
            String sobrenome,
            String documento,
            String endereco,
            String complemento,
            String cidade,
            Long idPais,
            String telefone,
            String cep,
            NovoPedidoRequest pedido) {

        return new NovaCompraRequest(
                email, nome, sobrenome, documento, endereco, complemento, cidade,
                idPais, null, telefone, cep, pedido, null
        );
    }
}

