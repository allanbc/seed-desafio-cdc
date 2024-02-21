package br.com.deveficiente.casadocodigov2.model.Categoria;

import br.com.deveficiente.casadocodigov2.entity.Categoria;
import br.com.deveficiente.casadocodigov2.util.UniqueValue;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CadastroCategoriaRequest (
        @NotBlank(message = "O nome é obrigatório")
        @UniqueValue(domainClass = Categoria.class, fieldName = "nome", message = "Essa categoria já está cadastrada")
        String nome){
}
