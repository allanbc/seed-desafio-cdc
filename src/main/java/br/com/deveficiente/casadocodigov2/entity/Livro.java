package br.com.deveficiente.casadocodigov2.entity;

import br.com.deveficiente.casadocodigov2.model.livro.NovoLivroRequest;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String resumo;
    private String sumario;
    private BigDecimal preco;
    private Integer numPaginas;
    private String isbn;
    private LocalDate dataPublicacao;
    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria categoria;
    @ManyToOne(fetch = FetchType.LAZY)
    private Autor autor;

    public Livro() {
    }
    public Livro(Autor autor, Categoria categoria, NovoLivroRequest request) {
        this.titulo = request.titulo();
        this.resumo = request.resumo();
        this.sumario = request.sumario();
        this.preco = request.preco();
        this.numPaginas = request.numPaginas();
        this.isbn = request.isbn();
        this.dataPublicacao = request.dataPublicacao();
        this.categoria = categoria;
        this.autor = autor;
    }

    public Livro(@NotBlank String titulo,
                 @NotBlank @Size(max = 500) String resumo, @NotBlank String sumario,
                 @NotNull @Min(20) BigDecimal preco, @Min(100) int numPaginas,
                 @NotBlank String isbn, @Future @NotNull LocalDate dataPublicacao,
                 @NotNull @Valid Autor autor, @NotNull @Valid Categoria categoria) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.sumario = sumario;
        this.preco = preco;
        this.numPaginas = numPaginas;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.autor = autor;
        this.categoria = categoria;
    }
}
