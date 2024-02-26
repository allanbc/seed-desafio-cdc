package br.com.deveficiente.casadocodigov2.model.autor;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import br.com.deveficiente.casadocodigov2.util.UniqueValue;
import jakarta.validation.constraints.*;

public record CadastroAutorRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "Email é obrigatório")
        @Email
        @UniqueValue(domainClass = Autor.class, fieldName = "email", message = "Existe autor cadastrado para o e-mail informado!")
        String email,
        @NotBlank(message = "Descrição é obrigatória")
        @Size(min = 3, max = 400)
        String descricao
    ) {
}
