package br.com.deveficiente.casadocodigov2.model.Categoria;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CadastroCategoriaRequest (
        @NotBlank(message = "O nome é obrigatório")
        String nome){
}
