package br.com.deveficiente.casadocodigov2.entity;

import br.com.deveficiente.casadocodigov2.model.Categoria.CadastroCategoriaRequest;
import br.com.deveficiente.casadocodigov2.model.Categoria.CategoriaResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@Entity
@ToString
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    public Categoria() {
    }

    public Categoria(CadastroCategoriaRequest request) {
        this.nome = request.nome();
    }
    public Categoria(Categoria categoria) {
    }
}
