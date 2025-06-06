package br.com.deveficiente.casadocodigov2.model.autor;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AutorResponse(Long id, String nome, String email, String descricao) {
    public AutorResponse(Autor autor) {
        this(autor.getId(), autor.getNome(), autor.getEmail(), autor.getDescricao());
    }
    public static Autor toResponse(Autor source) {
        return Autor.builder()
                .id(source.getId())
                .nome(source.getNome())
                .email(source.getEmail())
                .descricao(source.getDescricao())
                .build();
    }
}
