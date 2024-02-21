package br.com.deveficiente.casadocodigov2.model.Categoria;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import br.com.deveficiente.casadocodigov2.entity.Categoria;
import lombok.Builder;

@Builder
public record CategoriaResponse(String nome) {
    public CategoriaResponse(Categoria categoria) {
        this(categoria.getNome());
    }

    public static CategoriaResponse toResponse(Categoria categoria) {
        return CategoriaResponse.builder()
                .nome(categoria.getNome())
                .build();
    }
}
