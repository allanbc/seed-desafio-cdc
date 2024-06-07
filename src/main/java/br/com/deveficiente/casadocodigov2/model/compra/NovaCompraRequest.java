package br.com.deveficiente.casadocodigov2.model.compra;

import br.com.deveficiente.casadocodigov2.entity.Estado;
import br.com.deveficiente.casadocodigov2.entity.Pais;
import br.com.deveficiente.casadocodigov2.util.Documento;
import br.com.deveficiente.casadocodigov2.util.ExistsId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NovaCompraRequest(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String nome,
        @NotBlank
        String sobrenome,
        @NotBlank
        @Documento
        String documento,
        @NotBlank
        String endereco,
        @NotBlank
        String complemento,
        @NotBlank
        String cidade,
        @NotNull
        @ExistsId(domainClass = Pais.class, fieldName = "id")
        Long idPais,
        @NotNull
        @ExistsId(domainClass = Estado.class, fieldName = "id")
        Long idEstado,
        @NotBlank
        String telefone,
        @NotBlank
        String cep,
        @Valid
        @NotNull
        NovoPedidoRequest pedido) {
}
