package br.com.deveficiente.casadocodigov2.entity;

import br.com.deveficiente.casadocodigov2.model.autor.CadastroAutorRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String descricao;
    @CreationTimestamp(source = SourceType.VM)
    private LocalDateTime dateCreated;
    @UpdateTimestamp(source = SourceType.VM)
    private LocalDateTime lastUpdated;
    @OneToOne(fetch = FetchType.LAZY)
    private Livro livro;

    public Autor() {
    }
    public Autor(CadastroAutorRequest request) {
        this.nome = request.nome();
        this.email = request.email();
        this.descricao = request.descricao();
    }

    public Autor(Long id, String nome, String email, String descricao, LocalDateTime createdAt, LocalDateTime updatedAt, Livro livro) {
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
        this.dateCreated = createdAt;
        this.lastUpdated = updatedAt;
        this.livro = livro;
    }

    public Autor(String nome, String mail, String descricao) {
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
    }
}
