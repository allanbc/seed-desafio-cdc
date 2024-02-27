package br.com.deveficiente.casadocodigov2.model.categoria;

import br.com.deveficiente.casadocodigov2.entity.Categoria;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
 public record CategoriaResponse(Long id, String nome) {
    public CategoriaResponse(Categoria categoria) {
        this(categoria.getId(), categoria.getNome());
    }
    public static Categoria toResponse(Categoria categoria) {
        Categoria categoria1 = new Categoria();
        categoria1.setId(categoria.getId());
        categoria1.setNome(categoria.getNome());
        return categoria1;
    }
}
