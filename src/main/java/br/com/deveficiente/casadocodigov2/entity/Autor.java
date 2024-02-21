package br.com.deveficiente.casadocodigov2.entity;

import br.com.deveficiente.casadocodigov2.model.Autor.CadastroAutorRequest;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
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

    public Autor() {
    }
    public Autor(CadastroAutorRequest autorForm) {
        this.nome = autorForm.nome();
        this.email = autorForm.email();
        this.descricao = autorForm.descricao();
    }
}
