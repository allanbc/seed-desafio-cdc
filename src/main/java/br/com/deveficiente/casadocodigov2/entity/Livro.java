package br.com.deveficiente.casadocodigov2.entity;

import br.com.deveficiente.casadocodigov2.model.livro.CadastroLivroRequest;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String resumo;
    private String sumario;
    private BigDecimal preco;
    private Integer numPagina;
    private String isbn;
    private LocalDateTime dataPublicacao;
    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria categoria;
    @ManyToOne(fetch = FetchType.LAZY)
    private Autor autor;

    public Livro() {
    }
    public Livro(Autor autor, Categoria categoria, CadastroLivroRequest request) {
        this.titulo = request.titulo();
        this.resumo = request.resumo();
        this.sumario = request.sumario();
        this.preco = request.preco();
        this.numPagina = request.numPagina();
        this.isbn = request.isbn();
        this.dataPublicacao = request.dataPublicacao();
        this.categoria = categoria;
        this.autor = autor;
    }

}
