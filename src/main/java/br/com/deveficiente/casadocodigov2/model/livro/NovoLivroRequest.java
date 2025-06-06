package br.com.deveficiente.casadocodigov2.model.livro;


import br.com.deveficiente.casadocodigov2.entity.Autor;
import br.com.deveficiente.casadocodigov2.entity.Categoria;
import br.com.deveficiente.casadocodigov2.entity.Livro;
import br.com.deveficiente.casadocodigov2.util.ExistsId;
import br.com.deveficiente.casadocodigov2.util.UniqueValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;

public record NovoLivroRequest(
    @NotBlank(message = "O título é obrigatório")
    @UniqueValue(domainClass = Livro.class, fieldName = "titulo", message = "Existe um livro cadastrado com esse título!")
    String titulo,
    @NotBlank(message = "O resumo é obrigatório")
    @Size(max = 500, message = "O resumo não pode exceder a 500 caracteres")
    String resumo,
    String sumario,
    @NotNull(message = "O preço é obrigatório")
    @Min(value = 20)
    BigDecimal preco,
    @NotNull(message = "O número de páginas é obrigatório")
    @Min(value = 100)
    Integer numPaginas,
    @NotBlank(message = "O Isbn é obrigatório")
    @UniqueValue(domainClass = Livro.class, fieldName = "isbn", message = "O Isbn informado já está cadastrado para outro livro!")
    String isbn,
    @Future(message = "A data informada precisa ser maior que a data atual")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT-3")
    LocalDate dataPublicacao,
    @Valid
    @NotNull
    @ExistsId(domainClass = Categoria.class, fieldName = "id")
    Long idCategoria,
    @Valid
    @NotNull
    @ExistsId(domainClass = Autor.class, fieldName = "id")
    Long idAutor){

    public NovoLivroRequest(@NotBlank(message = "O título é obrigatório")
                            String titulo, @NotBlank(message = "O resumo é obrigatório")
                            @Size(max = 500, message = "O resumo não pode exceder a 500 caracteres")
                            String resumo, String sumario, @NotNull(message = "O preço é obrigatório")
                            @Min(value = 20)
                            BigDecimal preco, @NotNull(message = "O número de páginas é obrigatório")
                            @Min(value = 100)
                            Integer numPaginas, @NotBlank(message = "O Isbn é obrigatório")
                            String isbn, @Future(message = "A data informada precisa ser maior que a data atual")
                            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT-3")
                            LocalDate dataPublicacao, @Valid
                            @NotNull
                            Long idCategoria, @Valid
                            @NotNull
                            Long idAutor) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.sumario = sumario;
        this.preco = preco;
        this.numPaginas = numPaginas;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.idCategoria = idCategoria;
        this.idAutor = idAutor;
    }

    public Livro toModelLivro(Function<Long, Autor> buscaAutor,
                              Function<Long, Categoria> buscaCategoria) {
        Autor autor = buscaAutor.apply(idAutor);
        Categoria categoria = buscaCategoria.apply(idCategoria);
        Assert.state(true,"Você esta querendo cadastrar um livro para um autor que nao existe no banco " + idAutor);
        Assert.state(true,"Você esta querendo cadastrar um livro para uma categoria que nao existe no banco " + idCategoria);

        return new Livro(titulo, resumo, sumario, preco, numPaginas, isbn, dataPublicacao, autor, categoria);
    }
}

