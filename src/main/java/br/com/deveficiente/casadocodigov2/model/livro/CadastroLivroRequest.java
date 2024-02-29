package br.com.deveficiente.casadocodigov2.model.livro;


import br.com.deveficiente.casadocodigov2.entity.Autor;
import br.com.deveficiente.casadocodigov2.entity.Categoria;
import br.com.deveficiente.casadocodigov2.entity.Livro;
import br.com.deveficiente.casadocodigov2.util.ExistsId;
import br.com.deveficiente.casadocodigov2.util.UniqueValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
public record CadastroLivroRequest(
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
    Integer numPagina,
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
}

