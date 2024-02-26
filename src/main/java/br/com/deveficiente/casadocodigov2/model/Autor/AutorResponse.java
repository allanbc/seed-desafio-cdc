package br.com.deveficiente.casadocodigov2.model.autor;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AutorResponse(String nome, String email, String descricao) {
    public AutorResponse(Autor autor) {
        this(autor.getNome(), autor.getEmail(), autor.getDescricao());
    }
    public static Autor toResponse(Autor source) {
        Autor target = new Autor();
        target.setId(source.getId());
        target.setNome(source.getNome());
        target.setEmail(source.getEmail());
        target.setDescricao(source.getDescricao());
        return target;
    }
}
