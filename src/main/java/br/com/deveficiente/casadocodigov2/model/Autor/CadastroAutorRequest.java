package br.com.deveficiente.casadocodigov2.model.Autor;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record CadastroAutorRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "Email é obrigatório")
        @Email
        String email,
        @NotBlank(message = "Descrição é obrigatória")
        @Size(min = 3, max = 400)
        String descricao
    ) {
}
