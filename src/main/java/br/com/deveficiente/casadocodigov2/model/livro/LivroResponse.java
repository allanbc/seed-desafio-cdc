package br.com.deveficiente.casadocodigov2.model.livro;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import br.com.deveficiente.casadocodigov2.entity.Categoria;
import br.com.deveficiente.casadocodigov2.entity.Livro;
import br.com.deveficiente.casadocodigov2.model.autor.AutorResponse;
import br.com.deveficiente.casadocodigov2.model.categoria.CategoriaResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public record LivroResponse(
        Long id,
        String titulo,
        String resumo,
        BigDecimal preco,
        String isbn,
        Categoria categoria,
        Autor autor
        )
{
    public LivroResponse(Livro livro) {
        this(livro.getId(),
        livro.getTitulo(),
        livro.getResumo(),
        livro.getPreco(),
        livro.getIsbn(),
        Optional.ofNullable(livro.getCategoria()).map(CategoriaResponse::toResponse).orElse(null),
        Optional.ofNullable(livro.getAutor()).map(AutorResponse::toResponse).orElse(null));
    }
}
