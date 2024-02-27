package br.com.deveficiente.casadocodigov2.model.livro;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import br.com.deveficiente.casadocodigov2.entity.Livro;
import br.com.deveficiente.casadocodigov2.model.autor.AutorDetalheResponse;
import br.com.deveficiente.casadocodigov2.model.autor.AutorResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public record LivroDetalheResponse(
        Long id,
        String titulo,
        String resumo,
        String sumario,
        BigDecimal preco,
        Integer numPagina,
        String isbn,
        LocalDate dataPublicacao,
        Autor autor
        )
{
    public LivroDetalheResponse(Livro livro) {
        this(livro.getId(),
        livro.getTitulo(),
        livro.getResumo(),
        livro.getSumario(),
        livro.getPreco(),
        livro.getNumPagina(),
        livro.getIsbn(),
        livro.getDataPublicacao(),
        Optional.ofNullable(livro.getAutor()).map(AutorDetalheResponse::autorDetalheResponse).orElse(null));
    }
}
