package br.com.deveficiente.casadocodigov2.model.autor;

import br.com.deveficiente.casadocodigov2.entity.Autor;

public record AutorDetalheResponse(Long id, String nome) {
    public static Autor autorDetalheResponse(Autor source) {
        return Autor.builder()
                .id(source.getId())
                .nome(source.getNome())
                .build();
    }
}
