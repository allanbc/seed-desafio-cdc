package br.com.deveficiente.casadocodigov2.model.autor;

import br.com.deveficiente.casadocodigov2.entity.Autor;

public record AutorDetalheResponse(Long id, String nome) {
    public static Autor autorDetalheResponse(Autor source) {
        Autor target = new Autor();
        target.setId(source.getId());
        target.setNome(source.getNome());
        return target;
    }
}
