package br.com.deveficiente.casadocodigov2.model.compra;

import br.com.deveficiente.casadocodigov2.entity.Compra;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public record CompraResponse(
        Long id,
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
        String cep
) {
    public CompraResponse(Compra compra) {
        this(compra.getId(), compra.getEmail(), compra.getNome(), compra.getSobrenome(),
                compra.getDocumento(), compra.getEndereco(), compra.getComplemento(), compra.getCidade(),
                compra.getPais().getId(), compra.getEstado().getId(), compra.getTelefone(), compra.getCep());
    }
}
