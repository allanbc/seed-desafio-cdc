package br.com.deveficiente.casadocodigov2.model;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import lombok.Builder;

@Builder
public record AutorResponse(String nome, String email, String descricao) {
    public AutorResponse(Autor autor) {
        this(autor.getNome(), autor.getEmail(), autor.getDescricao());
    }

    public static AutorResponse toResponse(Autor autor) {
        return AutorResponse.builder()
                .nome(autor.getNome())
                .email(autor.getEmail())
                .descricao(autor.getDescricao())
                .build();
    }
}
